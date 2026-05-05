import { useEffect, useState } from 'react'
import { getRacesBySeason } from '../services/api'
import type { Race } from '../types'

export default function Races() {
    const [races, setRaces] = useState<Race[]>([])
    const [season, setSeason] = useState(2026)
    const [loading, setLoading] = useState(true)

    useEffect(() => {
        setLoading(true)
        getRacesBySeason(season)
            .then(res => setRaces(res.data))
            .finally(() => setLoading(false))
    }, [season])

    return (
        <div>
            <div className="flex items-center justify-between mb-6">
                <h2 className="text-2xl font-bold">Yarışlar</h2>
                <div className="flex gap-2">
                    {[2024, 2025, 2026].map(y => (
                        <button
                            key={y}
                            onClick={() => setSeason(y)}
                            className={`px-4 py-2 rounded-lg text-sm font-medium transition-colors ${
                                season === y
                                    ? 'bg-red-600 text-white'
                                    : 'bg-gray-800 text-gray-400 hover:text-white'
                            }`}
                        >
                            {y}
                        </button>
                    ))}
                </div>
            </div>

            {loading ? (
                <p className="text-gray-400">Yükleniyor...</p>
            ) : races.length === 0 ? (
                <p className="text-gray-500">Bu sezon için yarış bulunamadı.</p>
            ) : (
                <div className="overflow-hidden rounded-lg border border-gray-800">
                    <table className="w-full">
                        <thead className="bg-gray-900">
                        <tr>
                            <th className="text-left px-4 py-3 text-sm text-gray-400 font-medium">Yarış</th>
                            <th className="text-left px-4 py-3 text-sm text-gray-400 font-medium">Pist</th>
                            <th className="text-left px-4 py-3 text-sm text-gray-400 font-medium">Ülke</th>
                            <th className="text-left px-4 py-3 text-sm text-gray-400 font-medium">Tarih</th>
                        </tr>
                        </thead>
                        <tbody>
                        {races.map((race, i) => (
                            <tr
                                key={race.id}
                                className={`border-t border-gray-800 hover:bg-gray-900 transition-colors ${
                                    i % 2 === 0 ? 'bg-gray-950' : 'bg-gray-900/50'
                                }`}
                            >
                                <td className="px-4 py-3 font-medium">{race.name}</td>
                                <td className="px-4 py-3 text-gray-400 text-sm">{race.circuit}</td>
                                <td className="px-4 py-3 text-gray-400 text-sm">{race.country}</td>
                                <td className="px-4 py-3 text-gray-400 text-sm">
                                    {new Date(race.raceDate).toLocaleDateString('tr-TR', {
                                        day: 'numeric',
                                        month: 'long',
                                        year: 'numeric'
                                    })}
                                </td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                </div>
            )}
        </div>
    )
}