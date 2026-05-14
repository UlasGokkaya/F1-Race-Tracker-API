import axios from 'axios'
import type { Driver, Team, Race, DriverStanding, ConstructorStanding, Progression } from '../types'

const api = axios.create({ baseURL: '/api' })

export const getDrivers = () => api.get<Driver[]>('/drivers')
export const getTeams   = () => api.get<Team[]>('/teams')
export const getRaces   = () => api.get<Race[]>('/races')
export const getRacesBySeason = (year: number) => api.get<Race[]>(`/races/season/${year}`)
export const createDriver = (data: Omit<Driver, 'id'>) => api.post<Driver>('/drivers', data)
export const deleteDriver = (id: number) => api.delete(`/drivers/${id}`)
export const createTeam  = (data: Omit<Team, 'id'>) => api.post<Team>('/teams', data)
export const createRace  = (data: Omit<Race, 'id'>) => api.post<Race>('/races', data)
export const deleteRace  = (id: number) => api.delete(`/races/${id}`)
export const deleteTeam  = (id: number) => api.delete(`/teams/${id}`)

export const getDriverStandings     = (season: number) => api.get<DriverStanding[]>(`/standings/drivers/${season}`)
export const getConstructorStandings = (season: number) => api.get<ConstructorStanding[]>(`/standings/constructors/${season}`)
export const getProgression         = (season: number) => api.get<Progression>(`/standings/progression/${season}`)