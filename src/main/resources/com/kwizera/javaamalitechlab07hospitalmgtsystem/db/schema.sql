-- ROLE table
CREATE TABLE IF NOT EXISTS role (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE
);

-- EMPLOYEE table
CREATE TABLE IF NOT EXISTS employee (
    id SERIAL PRIMARY KEY,
    employee_no VARCHAR(50) UNIQUE NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    sur_name VARCHAR(100) NOT NULL,
    address TEXT,
    telephone_number VARCHAR(20),
    salary NUMERIC(10, 2),
    role_id INTEGER NOT NULL REFERENCES role(id)
);

-- DOCTOR table
CREATE TABLE IF NOT EXISTS doctor (
    id SERIAL PRIMARY KEY,
    employee_id INTEGER NOT NULL UNIQUE REFERENCES employee(id),
    specialty VARCHAR(100)
);

-- NURSE table
CREATE TABLE IF NOT EXISTS nurse (
    id SERIAL PRIMARY KEY,
    employee_id INTEGER NOT NULL UNIQUE REFERENCES employee(id),
    rotation VARCHAR(50)
);

-- DEPARTMENT table
CREATE TABLE IF NOT EXISTS department (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    director_id INTEGER NOT NULL REFERENCES employee(id)
);

-- WARD table
CREATE TABLE IF NOT EXISTS ward (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    supervisor_id INTEGER NOT NULL REFERENCES employee(id),
    department_id INTEGER NOT NULL REFERENCES department(id)
);

-- BED table
CREATE TABLE IF NOT EXISTS bed (
    id SERIAL PRIMARY KEY,
    ward_id INTEGER NOT NULL REFERENCES ward(id),
    bed_no VARCHAR(20) NOT NULL
);

-- PATIENT table
CREATE TABLE IF NOT EXISTS patient (
    id SERIAL PRIMARY KEY,
    patient_no VARCHAR(50) UNIQUE NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    sur_name VARCHAR(100) NOT NULL,
    address TEXT,
    telephone_number VARCHAR(20),
    dob DATE
);

-- HOSPITALIZATION table
CREATE TABLE IF NOT EXISTS hospitalization (
    id SERIAL PRIMARY KEY,
    patient_id INTEGER NOT NULL REFERENCES patient(id),
    start_date DATE NOT NULL,
    end_date DATE,
    notes TEXT
);

-- DIAGNOSTICS table
CREATE TABLE IF NOT EXISTS diagnostics (
    id SERIAL PRIMARY KEY,
    hospitalization_id INTEGER NOT NULL REFERENCES hospitalization(id),
    doctor_id INTEGER NOT NULL REFERENCES doctor(id),
    diagnosis TEXT,
    treatment TEXT,
    date DATE NOT NULL
);

-- ADMISSION table
CREATE TABLE IF NOT EXISTS admission (
    id SERIAL PRIMARY KEY,
    hospitalization_id INTEGER NOT NULL REFERENCES hospitalization(id),
    ward_id INTEGER NOT NULL REFERENCES ward(id),
    bed_id INTEGER NOT NULL REFERENCES bed(id),
    admitted_at TIMESTAMP NOT NULL,
    discharged_at TIMESTAMP,
    notes TEXT
);
