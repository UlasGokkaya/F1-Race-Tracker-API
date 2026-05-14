import { Link, useLocation } from 'react-router-dom'

const links = [
    { to: '/',          label: '🏠 Dashboard'   },
    { to: '/drivers',   label: '🧑‍✈️ Sürücüler' },
    { to: '/teams',     label: '🏎️ Takımlar'    },
    { to: '/races',     label: '🏁 Yarışlar'    },
    { to: '/standings', label: '🏆 Sıralama'    },
]

export default function Navbar() {
    const { pathname } = useLocation()

    return (
        <nav className="bg-gray-900 border-b border-gray-800 px-6 py-4">
            <div className="max-w-6xl mx-auto flex items-center justify-between">
                <span className="text-red-500 font-bold text-xl tracking-tight">F1 TRACKER</span>
                <div className="flex gap-2">
                    {links.map(link => (
                        <Link
                            key={link.to}
                            to={link.to}
                            className={`px-4 py-2 rounded-lg text-sm font-medium transition-colors ${
                                pathname === link.to
                                    ? 'bg-red-600 text-white'
                                    : 'text-gray-400 hover:text-white hover:bg-gray-800'
                            }`}
                        >
                            {link.label}
                        </Link>
                    ))}
                </div>
            </div>
        </nav>
    )
}