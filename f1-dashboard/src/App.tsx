import { BrowserRouter, Routes, Route } from 'react-router-dom'
import Navbar from './components/Navbar'
import Dashboard from './pages/Dashboard'
import Drivers from './pages/Drivers'
import Teams from './pages/Teams'
import Races from './pages/Races'
import Standings from './pages/Standings'

export default function App() {
  return (
      <BrowserRouter>
        <div className="min-h-screen bg-gray-950 text-white">
          <Navbar />
          <main className="max-w-6xl mx-auto px-6 py-8">
            <Routes>
              <Route path="/"          element={<Dashboard />}  />
              <Route path="/drivers"   element={<Drivers />}    />
              <Route path="/teams"     element={<Teams />}      />
              <Route path="/races"     element={<Races />}      />
              <Route path="/standings" element={<Standings />}  />
            </Routes>
          </main>
        </div>
      </BrowserRouter>
  )
}
