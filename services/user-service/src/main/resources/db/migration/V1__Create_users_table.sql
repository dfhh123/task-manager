-- =====================================================
-- User Service Database Schema - Keycloak Integration
-- =====================================================
-- This table stores minimal USER PROFILE data.
-- Authentication (username, password, roles) is handled by Keycloak.
-- The keycloak_id column links this profile to Keycloak user.
-- 
-- This is a minimal version with essential fields only.
-- Additional fields can be added later as needed.
-- =====================================================

CREATE TABLE users (
    -- Primary Key
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    
    -- ⭐ REQUIRED: Link to Keycloak user (subject claim from JWT)
    keycloak_id VARCHAR(255) UNIQUE NOT NULL,
    
    -- Basic Profile Info (synced from Keycloak)
    email VARCHAR(255) UNIQUE NOT NULL,
    
    -- User Preferences
    timezone VARCHAR(50) NOT NULL DEFAULT 'UTC',
    language VARCHAR(10) NOT NULL DEFAULT 'en',
    
    -- Metadata
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    
    -- Constraints
    CONSTRAINT email_format_check CHECK (email ~* '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$'),
    CONSTRAINT timezone_not_empty CHECK (length(timezone) > 0),
    CONSTRAINT language_valid CHECK (length(language) BETWEEN 2 AND 10)
);

-- =====================================================
-- Indexes for Performance
-- =====================================================

-- Most important: fast lookup by Keycloak ID (used in every authenticated request)
CREATE UNIQUE INDEX idx_users_keycloak_id ON users(keycloak_id);

-- Lookup by email (for admin operations and sync)
CREATE UNIQUE INDEX idx_users_email ON users(email);

-- Sorting by creation date
CREATE INDEX idx_users_created_at ON users(created_at DESC);

-- =====================================================
-- Triggers
-- =====================================================

-- Auto-update updated_at timestamp on row modification
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER update_users_updated_at 
    BEFORE UPDATE ON users 
    FOR EACH ROW 
    EXECUTE FUNCTION update_updated_at_column();

-- =====================================================
-- Comments for Documentation
-- =====================================================

COMMENT ON TABLE users IS 'Minimal user profiles synchronized with Keycloak. Authentication is handled by Keycloak.';
COMMENT ON COLUMN users.keycloak_id IS 'Keycloak user ID (sub claim from JWT token). Primary link to Keycloak user.';
COMMENT ON COLUMN users.email IS 'User email address. Synchronized from Keycloak on user creation/update.';
COMMENT ON COLUMN users.timezone IS 'User timezone preference (e.g., UTC, Europe/London, America/New_York).';
COMMENT ON COLUMN users.language IS 'User language preference (ISO 639-1 code, e.g., en, ru, es).';
COMMENT ON COLUMN users.created_at IS 'Timestamp when user profile was created.';
COMMENT ON COLUMN users.updated_at IS 'Timestamp when user profile was last updated. Auto-updated by trigger.';
