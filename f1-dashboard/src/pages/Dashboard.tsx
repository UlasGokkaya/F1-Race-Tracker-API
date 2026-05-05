import { useEffect, useState } from 'react'
import { getDrivers, getTeams, getRaces } from '../services/api'

interface Stats {
    drivers: number
    teams: number
    races: number
}

export default function Dashboard() {
    const [stats, setStats] = useState<Stats>({ drivers: 0, teams: 0, races: 0 })
    const [loading, setLoading] = useState(true)

    useEffect(() => {
        Promise.all([getDrivers(), getTeams(), getRaces()])
            .then(([d, t, r]) => {
                setStats({
                    drivers: d.data.length,
                    teams: t.data.length,
                    races: r.data.length,
                })
            })
            .finally(() => setLoading(false))
    }, [])

    if (loading) return <p className="text-gray-400">Yükleniyor...</p>

    const cards = [
        { label: 'Sürücüler', value: stats.drivers, icon: '🧑‍✈️', color: 'border-red-500' },
        { label: 'Takımlar',  value: stats.teams,   icon: '🏎️',  color: 'border-blue-500' },
        { label: 'Yarışlar',  value: stats.races,   icon: '🏁',  color: 'border-yellow-500' },
    ]

    return (
        <div>
            <h2 className="text-2xl font-bold mb-6">Genel Bakış</h2>
            <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
                {cards.map(card => (
                    <div
                        key={card.label}
                        className={`bg-gray-900 border-l-4 ${card.color} rounded-lg p-6`}
                    >
                        <div className="text-3xl mb-2">{card.icon}</div>
                        <div className="text-4xl font-bold mb-1">{card.value}</div>
                        <div className="text-gray-400 text-sm">{card.label}</div>
                    </div>
                ))}
            </div>
        </div>
    )
}