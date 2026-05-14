export interface Team {
    id: number
    name: string
    nationality: string
    foundedYear: number
}

export interface Driver {
    id: number
    firstName: string
    lastName: string
    nationality: string
    racingNumber: number
    team: Team
}

export interface Race {
    id: number
    name: string
    circuit: string
    country: string
    raceDate: string
    season: number
}

export interface DriverStanding {
    position: number
    name: string
    team: string
    racingNumber: number | null
    points: number
    wins: number
}

export interface ConstructorStanding {
    position: number
    team: string
    points: number
    wins: number
}

export interface DriverProgression {
    name: string
    team: string
    cumulativePoints: number[]
}

export interface Progression {
    rounds: string[]
    drivers: DriverProgression[]
}