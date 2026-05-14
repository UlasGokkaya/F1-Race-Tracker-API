export const teamColors: Record<string, { primary: string; secondary: string }> = {
  'Red Bull Racing':        { primary: '#3671C6', secondary: '#CC1E4A' },
  'Mercedes-AMG Petronas':  { primary: '#27F4D2', secondary: '#1C1C1C' },
  'Scuderia Ferrari':       { primary: '#E8002D', secondary: '#FFCD00' },
  'McLaren':                { primary: '#FF8000', secondary: '#1C1C1C' },
  'Aston Martin':           { primary: '#229971', secondary: '#1C1C1C' },
  'Alpine':                 { primary: '#FF87BC', secondary: '#0093CC' },
  'Williams':               { primary: '#64C4FF', secondary: '#1C1C1C' },
  'Visa Cash App RB':       { primary: '#6692FF', secondary: '#1C1C1C' },
  'Haas F1 Team':           { primary: '#B6BABD', secondary: '#E8002D' },
  'Audi F1 Team':           { primary: '#C0C0C0', secondary: '#1C1C1C' },
  'Cadillac F1 Team':       { primary: '#333399', secondary: '#CC0000' },
}

export const getTeamColor = (teamName: string) =>
  teamColors[teamName] ?? { primary: '#6B7280', secondary: '#1C1C1C' }