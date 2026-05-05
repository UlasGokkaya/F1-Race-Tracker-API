import { useEffect, useState } from 'react'
import { getTeams } from '../services/api'
import type { Team } from '../types'

export default function Teams() {
    const [teams, setTeams] = useState<Team[]>([])
    const [loading, setLoading] = useState(true)

    useEffect(() => {
        getTeams()
            .then(res => setTeams(res.data))
            .finally(() => setLoading(false))
    }, [])

    if (loading) return <p className="text-gray-400">Yükleniyor...</p>

    return (
        <div>
            <h2 className="text-2xl font-bold mb-6">Takımlar</h2>
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
                {teams.map(team => (
                    <div key={team.id} className="bg-gray-900 rounded-lg p-5 border border-gray-800 hover:border-blue-500 transition-colors">
                        <div className="flex items-center justify-between mb-3">
                            <span className="text-2xl">🏎️</span>
                            <span className="text-xs text-gray-500 bg-gray-800 px-2 py-1 rounded">{team.nationality}</span>
                        </div>
                        <div className="text-lg font-semibold">{team.name}</div>
                        <div className="text-sm text-gray-400 mt-1">Kuruluş: {team.foundedYear}</div>
                    </div>
                ))}
            </div>
        </div>
    )
}