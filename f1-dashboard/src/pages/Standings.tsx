import { useEffect, useState } from 'react'
import {
    LineChart, Line, XAxis, YAxis, CartesianGrid,
    Tooltip, Legend, ResponsiveContainer
} from 'recharts'
import { getDriverStandings, getConstructorStandings, getProgression } from '../services/api'
import type { DriverStanding, ConstructorStanding, Progression } from '../types'
import { getTeamColor } from '../constants/teamColors'

const SEASONS = [2025, 2024]
type Tab = 'drivers' | 'constructors'

export default function Standings() {
    const [season, setSeason]               = useState(2025)
    const [tab, setTab]                     = useState<Tab>('drivers')
    const [driverStandings, setDrivers]     = useState<DriverStanding[]>([])
    const [constructorStandings, setTeams]  = useState<ConstructorStanding[]>([])
    const [progression, setProgression]     = useState<Progression | null>(null)
    const [loading, setLoading]             = useState(true)

    useEffect(() => {
        setLoading(true)
        Promise.all([
            getDriverStandings(season),
            getConstructorStandings(season),
            getProgression(season),
        ]).then(([d, c, p]) => {
            setDrivers(d.data)
            setTeams(c.data)
            setProgression(p.data)
        }).finally(() => setLoading(false))
    }, [season])

    // Build data array for Recharts: [{name:'Bahrain', 'Max Verstappen':25, ...}, ...]
    const top10 = progression?.drivers.slice(0, 10) ?? []
    const chartData = (progression?.rounds ?? []).map((round, i) => {
        const entry: Record<string, string | number> = { name: round }
        top10.forEach(d => { entry[d.name] = d.cumulativePoints[i] ?? 0 })
        return entry
    })

    return (
        <div>
            {/* Header */}
            <div className="flex items-center justify-between mb-6">
                <h2 className="text-2xl font-bold">Şampiyona Sıralaması</h2>
                <select
                    value={season}
                    onChange={e => setSeason(Number(e.target.value))}
                    className="bg-gray-800 border border-gray-700 text-white text-sm rounded-lg px-3 py-2 focus:outline-none focus:border-red-500"
                >
                    {SEASONS.map(y => <option key={y} value={y}>{y}</option>)}
                </select>
            </div>

            {/* Tabs */}
            <div className="flex gap-2 mb-6">
                {(['drivers', 'constructors'] as Tab[]).map(t => (
                    <button
                        key={t}
                        onClick={() => setTab(t)}
                        className={`px-5 py-2 rounded-lg text-sm font-medium transition-colors ${
                            tab === t
                                ? 'bg-red-600 text-white'
                                : 'text-gray-400 hover:text-white hover:bg-gray-800'
                        }`}
                    >
                        {t === 'drivers' ? '🧑‍✈️ Pilotlar' : '🏎️ Takımlar'}
                    </button>
                ))}
            </div>

            {loading ? (
                <p className="text-gray-400">Yükleniyor...</p>
            ) : (
                <>
                    {/* Standings Table */}
                    {tab === 'drivers' ? (
                        <DriverTable standings={driverStandings} />
                    ) : (
                        <ConstructorTable standings={constructorStandings} />
                    )}

                    {/* Line Chart — only on drivers tab */}
                    {tab === 'drivers' && chartData.length > 0 && (
                        <div className="mt-10">
                            <h3 className="text-lg font-semibold mb-4 text-gray-200">
                                Puan Gelişimi — Top 10 Pilot
                            </h3>
                            <div className="bg-gray-900 border border-gray-800 rounded-xl p-4">
                                <ResponsiveContainer width="100%" height={380}>
                                    <LineChart data={chartData} margin={{ top: 5, right: 20, left: 0, bottom: 5 }}>
                                        <CartesianGrid strokeDasharray="3 3" stroke="#374151" />
                                        <XAxis
                                            dataKey="name"
                                            tick={{ fill: '#9CA3AF', fontSize: 11 }}
                                            interval="preserveStartEnd"
                                        />
                                        <YAxis tick={{ fill: '#9CA3AF', fontSize: 11 }} />
                                        <Tooltip
                                            contentStyle={{ backgroundColor: '#111827', border: '1px solid #374151', borderRadius: 8 }}
                                            labelStyle={{ color: '#F9FAFB', fontWeight: 600 }}
                                            itemStyle={{ color: '#D1D5DB' }}
                                        />
                                        <Legend wrapperStyle={{ fontSize: 12, color: '#9CA3AF' }} />
                                        {top10.map(d => (
                                            <Line
                                                key={d.name}
                                                type="monotone"
                                                dataKey={d.name}
                                                stroke={getTeamColor(d.team).primary}
                                                strokeWidth={2}
                                                dot={false}
                                                activeDot={{ r: 4 }}
                                            />
                                        ))}
                                    </LineChart>
                                </ResponsiveContainer>
                            </div>
                        </div>
                    )}
                </>
            )}
        </div>
    )
}

function DriverTable({ standings }: { standings: DriverStanding[] }) {
    if (standings.length === 0)
        return <p className="text-gray-500 text-sm">Bu sezon için veri yok. Önce sync yapın: POST /api/sync/{'<season>'}</p>

    return (
        <div className="overflow-x-auto">
            <table className="w-full text-sm">
                <thead>
                    <tr className="text-gray-400 border-b border-gray-800">
                        <th className="text-left py-3 px-2 w-10">#</th>
                        <th className="text-left py-3 px-2">Pilot</th>
                        <th className="text-left py-3 px-2">Takım</th>
                        <th className="text-right py-3 px-2">Puan</th>
                        <th className="text-right py-3 px-2">Zafer</th>
                    </tr>
                </thead>
                <tbody>
                    {standings.map(s => {
                        const color = getTeamColor(s.team).primary
                        return (
                            <tr key={s.position} className="border-b border-gray-800/50 hover:bg-gray-900 transition-colors">
                                <td className="py-3 px-2 font-bold text-gray-400">{s.position}</td>
                                <td className="py-3 px-2">
                                    <div className="flex items-center gap-2">
                                        <span
                                            className="w-1 h-6 rounded-full shrink-0"
                                            style={{ backgroundColor: color }}
                                        />
                                        <span className="font-semibold">{s.name}</span>
                                        {s.racingNumber && (
                                            <span className="text-xs text-gray-500">#{s.racingNumber}</span>
                                        )}
                                    </div>
                                </td>
                                <td className="py-3 px-2 text-gray-400">{s.team}</td>
                                <td className="py-3 px-2 text-right font-bold text-white">{s.points}</td>
                                <td className="py-3 px-2 text-right text-yellow-400">{s.wins > 0 ? `${s.wins} 🏆` : '-'}</td>
                            </tr>
                        )
                    })}
                </tbody>
            </table>
        </div>
    )
}

function ConstructorTable({ standings }: { standings: ConstructorStanding[] }) {
    if (standings.length === 0)
        return <p className="text-gray-500 text-sm">Bu sezon için veri yok. Önce sync yapın: POST /api/sync/{'<season>'}</p>

    return (
        <div className="overflow-x-auto">
            <table className="w-full text-sm">
                <thead>
                    <tr className="text-gray-400 border-b border-gray-800">
                        <th className="text-left py-3 px-2 w-10">#</th>
                        <th className="text-left py-3 px-2">Takım</th>
                        <th className="text-right py-3 px-2">Puan</th>
                        <th className="text-right py-3 px-2">Zafer</th>
                    </tr>
                </thead>
                <tbody>
                    {standings.map(s => {
                        const color = getTeamColor(s.team).primary
                        return (
                            <tr key={s.position} className="border-b border-gray-800/50 hover:bg-gray-900 transition-colors">
                                <td className="py-3 px-2 font-bold text-gray-400">{s.position}</td>
                                <td className="py-3 px-2">
                                    <div className="flex items-center gap-2">
                                        <span
                                            className="w-3 h-3 rounded-full shrink-0"
                                            style={{ backgroundColor: color }}
                                        />
                                        <span className="font-semibold">{s.team}</span>
                                    </div>
                                </td>
                                <td className="py-3 px-2 text-right font-bold text-white">{s.points}</td>
                                <td className="py-3 px-2 text-right text-yellow-400">{s.wins > 0 ? `${s.wins} 🏆` : '-'}</td>
                            </tr>
                        )
                    })}
                </tbody>
            </table>
        </div>
    )
}
