import { useEffect, useState } from 'react'
import { getDrivers } from '../services/api'
import type { Driver } from '../types'

export default function Drivers() {
    const [drivers, setDrivers] = useState<Driver[]>([])
    const [loading, setLoading] = useState(true)

    useEffect(() => {
        getDrivers()
            .then(res => setDrivers(res.data))
            .finally(() => setLoading(false))
    }, [])

    if (loading) return <p className="text-gray-400">Yükleniyor...</p>

    return (
        <div>
            <h2 className="text-2xl font-bold mb-6">Sürücüler</h2>
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
                {drivers.map(driver => (
                    <div key={driver.id} className="bg-gray-900 rounded-lg p-5 border border-gray-800 hover:border-red-500 transition-colors">
                        <div className="flex items-center justify-between mb-3">
                            <span className="text-3xl font-bold text-red-500">#{driver.racingNumber}</span>
                            <span className="text-xs text-gray-500 bg-gray-800 px-2 py-1 rounded">{driver.nationality}</span>
                        </div>
                        <div className="text-lg font-semibold">{driver.firstName} {driver.lastName}</div>
                        <div className="text-sm text-gray-400 mt-1">{driver.team?.name}</div>
                    </div>
                ))}
            </div>
        </div>
    )
}