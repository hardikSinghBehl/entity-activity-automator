CREATE TABLE doctors (
  id UUID PRIMARY KEY,
  email_id VARCHAR(50) NOT NULL UNIQUE,
  password VARCHAR(150) NOT NULL
);

CREATE TABLE patients (
  id UUID PRIMARY KEY,
  full_name VARCHAR(100) NOT NULL,
  is_active BIT(1) NOT NULL,
  created_at TIMESTAMP NOT NULL,
  created_by UUID NOT NULL, 
  updated_at TIMESTAMP NOT NULL,
  updated_by UUID NOT NULL
);

CREATE TABLE appointments (
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  patient_id UUID NOT NULL,
  scheduled_at TIMESTAMP NOT NULL,
  is_active BIT(1) NOT NULL,
  created_at TIMESTAMP NOT NULL,
  created_by UUID NOT NULL, 
  updated_at TIMESTAMP NOT NULL,
  updated_by UUID NOT NULL,
  CONSTRAINT `appointment_fkey_patients` FOREIGN KEY (patient_id) 
  REFERENCES patients (id)
);