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