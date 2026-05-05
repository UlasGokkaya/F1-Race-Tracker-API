-- Takımlar (2026)
INSERT INTO teams (name, nationality, founded_year) VALUES
('Red Bull Racing', 'Austrian', 2005),
('Mercedes-AMG Petronas', 'German', 1954),
('Scuderia Ferrari', 'Italian', 1929),
('McLaren', 'British', 1963),
('Aston Martin', 'British', 2021),
('Alpine', 'French', 2021),
('Williams', 'British', 1977),
('Visa Cash App RB', 'Italian', 2006),
('Haas F1 Team', 'American', 2016),
('Audi F1 Team', 'German', 2026),
('Cadillac F1 Team', 'American', 2026)
ON CONFLICT (name) DO NOTHING;

-- Sürücüler (2026 grid)
INSERT INTO drivers (first_name, last_name, nationality, racing_number, team_id) VALUES
('Max', 'Verstappen', 'Dutch', 1, 1),
('Isack', 'Hadjar', 'French', 6, 1),
('George', 'Russell', 'British', 63, 2),
('Kimi', 'Antonelli', 'Italian', 12, 2),
('Charles', 'Leclerc', 'Monegasque', 16, 3),
('Lewis', 'Hamilton', 'British', 44, 3),
('Lando', 'Norris', 'British', 4, 4),
('Oscar', 'Piastri', 'Australian', 81, 4),
('Fernando', 'Alonso', 'Spanish', 14, 5),
('Lance', 'Stroll', 'Canadian', 18, 5),
('Pierre', 'Gasly', 'French', 10, 6),
('Franco', 'Colapinto', 'Argentine', 43, 6),
('Alex', 'Albon', 'Thai', 23, 7),
('Carlos', 'Sainz', 'Spanish', 55, 7),
('Liam', 'Lawson', 'New Zealander', 30, 8),
('Arvid', 'Lindblad', 'Swedish', 5, 8),
('Oliver', 'Bearman', 'British', 87, 9),
('Esteban', 'Ocon', 'French', 31, 9),
('Nico', 'Hulkenberg', 'German', 27, 10),
('Gabriel', 'Bortoleto', 'Brazilian', 21, 10),
('Sergio', 'Perez', 'Mexican', 11, 11),
('Valtteri', 'Bottas', 'Finnish', 77, 11)
ON CONFLICT DO NOTHING;