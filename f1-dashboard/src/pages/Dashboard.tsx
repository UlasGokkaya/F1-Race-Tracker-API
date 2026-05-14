import { useEffect, useState } from "react";
import { getDrivers, getTeams, getRaces } from "../services/api";
import type { Race } from "../types";

interface Stats {
  drivers: number;
  teams: number;
  races: number;
  allRaces: Race[];
}

function getCountdown(dateStr: string) {
  const diff = new Date(dateStr).getTime() - new Date().getTime();
  if (diff <= 0) return { days: 0, hours: 0, minutes: 0, seconds: 0 };
  return {
    days: Math.floor(diff / (1000 * 60 * 60 * 24)),
    hours: Math.floor((diff / (1000 * 60 * 60)) % 24),
    minutes: Math.floor((diff / (1000 * 60)) % 60),
    seconds: Math.floor((diff / 1000) % 60),
  };
}

function CountdownBox({ value, label }: { value: number; label: string }) {
  return (
    <div className="flex flex-col items-center bg-gray-800 rounded-xl px-4 py-3 min-w-[64px]">
      <span className="text-3xl font-bold text-white tabular-nums font-mono">
        {String(value).padStart(2, "0")}
      </span>
      <span className="text-xs text-gray-400 mt-1 uppercase tracking-widest">
        {label}
      </span>
    </div>
  );
}

export default function Dashboard() {
  const [stats, setStats] = useState<Stats>({
    drivers: 0,
    teams: 0,
    races: 0,
    allRaces: [],
  });
  const [countdown, setCountdown] = useState({
    days: 0,
    hours: 0,
    minutes: 0,
    seconds: 0,
  });
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    Promise.all([getDrivers(), getTeams(), getRaces()])
      .then(([d, t, r]) => {
        setStats({
          drivers: d.data.length,
          teams: t.data.length,
          races: r.data.length,
          allRaces: r.data,
        });
      })
      .finally(() => setLoading(false));
  }, []);

  const today = new Date();
  const upcomingRaces = stats.allRaces
    .filter((r) => new Date(r.raceDate) >= today)
    .sort(
      (a, b) => new Date(a.raceDate).getTime() - new Date(b.raceDate).getTime(),
    );
  const nextRace = upcomingRaces[0] || null;
  const nextSixRaces = upcomingRaces.slice(0, 6);

  useEffect(() => {
    if (!nextRace) return;
    setCountdown(getCountdown(nextRace.raceDate));
    const timer = setInterval(
      () => setCountdown(getCountdown(nextRace.raceDate)),
      1000,
    );
    return () => clearInterval(timer);
  }, [nextRace]);

  if (loading)
    return (
      <div className="flex items-center justify-center h-64">
        <div className="w-8 h-8 border-2 border-red-500 border-t-transparent rounded-full animate-spin" />
      </div>
    );

  const statCards = [
    {
      label: "Sürücüler",
      value: stats.drivers,
      icon: "🧑‍✈️",
      border: "border-red-500",
      bg: "from-red-950/40",
    },
    {
      label: "Takımlar",
      value: stats.teams,
      icon: "🏎️",
      border: "border-blue-500",
      bg: "from-blue-950/40",
    },
    {
      label: "Yarışlar",
      value: stats.races,
      icon: "🏁",
      border: "border-yellow-500",
      bg: "from-yellow-950/40",
    },
  ];

  return (
    <div className="space-y-8">
      {/* Stat kartları */}
      <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
        {statCards.map((card) => (
          <div
            key={card.label}
            className={`bg-gradient-to-br ${card.bg} to-gray-900 border-l-4 ${card.border} rounded-xl p-6 border border-gray-800`}
          >
            <div className="text-3xl mb-3">{card.icon}</div>
            <div className="text-5xl font-bold mb-1 tabular-nums">
              {card.value}
            </div>
            <div className="text-gray-400 text-sm uppercase tracking-wider">
              {card.label}
            </div>
          </div>
        ))}
      </div>

      {/* Geri sayım */}
      {nextRace && (
        <div className="rounded-xl border border-gray-800 overflow-hidden">
          <div className="bg-red-600 px-6 py-3 flex items-center justify-between">
            <span className="text-white font-bold text-sm uppercase tracking-widest flex items-center gap-2">
              🏁 Sıradaki Yarış
            </span>
            <span className="text-red-200 text-xs">
              {new Date(nextRace.raceDate).toLocaleDateString("tr-TR", {
                day: "numeric",
                month: "long",
                year: "numeric",
              })}
            </span>
          </div>
          <div className="bg-gray-900 p-6">
            <div className="flex flex-col md:flex-row md:items-center justify-between gap-6">
              <div>
                <h3 className="text-2xl font-bold text-white">
                  {nextRace.name}
                </h3>
                <p className="text-gray-400 mt-1 text-sm">
                  🏟️ {nextRace.circuit}
                </p>
                <p className="text-gray-400 text-sm">🌍 {nextRace.country}</p>
              </div>
              <div className="flex gap-2">
                <CountdownBox value={countdown.days} label="Gün" />
                <CountdownBox value={countdown.hours} label="Saat" />
                <CountdownBox value={countdown.minutes} label="Dak" />
                <CountdownBox value={countdown.seconds} label="Sn" />
              </div>
            </div>
          </div>
        </div>
      )}

      {/* Yaklaşan 6 yarış */}
      <div>
        <h2 className="text-xl font-bold mb-4 flex items-center gap-2">
          📅 <span>Yaklaşan Yarışlar</span>
        </h2>
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-3">
          {nextSixRaces.map((race, i) => (
            <div
              key={race.id}
              className={`rounded-xl p-4 border transition-colors hover:border-red-500 ${
                i === 0
                  ? "border-red-500 bg-red-950/20"
                  : "border-gray-800 bg-gray-900"
              }`}
            >
              <div className="flex items-center justify-between mb-3">
                <span
                  className={`text-xs font-bold px-2 py-1 rounded-full ${
                    i === 0
                      ? "bg-red-600 text-white"
                      : "bg-gray-800 text-gray-400"
                  }`}
                >
                  {(() => {
                    const sorted = [...stats.allRaces].sort(
                      (a, b) =>
                        new Date(a.raceDate).getTime() -
                        new Date(b.raceDate).getTime(),
                    );
                    const roundNum =
                      sorted.findIndex((r) => r.id === race.id) + 1;
                    return i === 0
                      ? `SONRAKI · R${roundNum}`
                      : `Round ${roundNum}`;
                  })()}
                </span>
                <span className="text-xs text-gray-500">
                  {new Date(race.raceDate).toLocaleDateString("tr-TR", {
                    day: "numeric",
                    month: "short",
                  })}
                </span>
              </div>
              <div className="font-semibold text-white text-sm">
                {race.name}
              </div>
              <div className="text-xs text-gray-400 mt-1">
                🌍 {race.country}
              </div>
              <div className="text-xs text-gray-500 mt-0.5">
                🏟️ {race.circuit}
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}
