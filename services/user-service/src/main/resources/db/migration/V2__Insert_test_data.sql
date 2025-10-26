-- =====================================================
-- Test Data for Local Development
-- =====================================================
-- This migration inserts sample users for testing.
-- These keycloak_id values should match users created in your local Keycloak instance.
-- =====================================================

-- Admin User (linked to Keycloak admin user)
INSERT INTO users (
    id,
    keycloak_id,
    email,
    timezone,
    language
) VALUES (
    '00000000-0000-0000-0000-000000000001'::uuid,
    'admin-keycloak-id-123',  -- ⚠️ Replace with actual Keycloak admin user ID
    'admin@superpet.com',
    'UTC',
    'en'
);

-- Regular User 1 (US timezone)
INSERT INTO users (
    id,
    keycloak_id,
    email,
    timezone,
    language
) VALUES (
    '00000000-0000-0000-0000-000000000002'::uuid,
    'user1-keycloak-id-456',  -- ⚠️ Replace with actual Keycloak user ID
    'john.doe@example.com',
    'America/New_York',
    'en'
);

-- Regular User 2 (UK timezone)
INSERT INTO users (
    id,
    keycloak_id,
    email,
    timezone,
    language
) VALUES (
    '00000000-0000-0000-0000-000000000003'::uuid,
    'user2-keycloak-id-789',  -- ⚠️ Replace with actual Keycloak user ID
    'jane.smith@example.com',
    'Europe/London',
    'en'
);

-- User with Russian language preference
INSERT INTO users (
    id,
    keycloak_id,
    email,
    timezone,
    language
) VALUES (
    '00000000-0000-0000-0000-000000000004'::uuid,
    'user3-keycloak-id-101',  -- ⚠️ Replace with actual Keycloak user ID
    'ivan.petrov@example.com',
    'Europe/Moscow',
    'ru'
);

-- =====================================================
-- How to Use These Test Users
-- =====================================================
-- 
-- Option 1: Manual Keycloak Setup
-- 1. Start Keycloak locally
-- 2. Create users in Keycloak Admin Console with matching emails
-- 3. Get their Keycloak user IDs (UUID in Keycloak user details)
-- 4. Update keycloak_id values above to match
-- 5. Run this migration
--
-- Option 2: Automated Sync (Recommended)
-- 1. Create users in Keycloak
-- 2. Configure Keycloak Event Listener to send events to your service
-- 3. Let User Service auto-create profiles based on Keycloak events
-- 4. This migration is then optional (only for initial dev setup)
--
-- Getting Keycloak User ID:
-- - From Admin Console: Realm → Users → Click user → See ID field
-- - From JWT token: Extract 'sub' claim after user login
-- - From Keycloak Admin API: GET /admin/realms/{realm}/users
--
-- =====================================================

