-- Enable UUID extension if not already enabled
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- 1. Table: medicines
CREATE TABLE medicines (
                           id BIGSERIAL PRIMARY KEY,
                           name VARCHAR(255) NOT NULL UNIQUE,
                           about TEXT,
                           price BIGINT,
                           average_rating INT,
                           version INT,

                           CONSTRAINT rate_cant_exceed_5 CHECK (average_rating <= 5)
);

-- 2. Table: med_batches
CREATE TABLE med_batches (
                             id BIGSERIAL PRIMARY KEY,
                             med_id BIGINT,
                             amount_present BIGINT,
                             expiry DATE,
                             version INT,

                             CONSTRAINT fk_med_batches_medicines FOREIGN KEY (med_id) REFERENCES medicines(id)
);
CREATE INDEX idx_med_batches_med_id ON med_batches(med_id);

-- 3. Table: inventory_managers
CREATE TABLE inventory_managers (
                                    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                                    name VARCHAR(255) NOT NULL UNIQUE,
                                    email VARCHAR(255) NOT NULL UNIQUE,
                                    is_admin BOOLEAN,
                                    version INT
);

-- 4. Table: pharmacies
CREATE TABLE pharmacies (
                            id BIGSERIAL PRIMARY KEY,
                            name VARCHAR(255) NOT NULL UNIQUE,
                            street_address VARCHAR(255) NOT NULL,
                            city VARCHAR(100) NOT NULL,
                            region VARCHAR(100) NOT NULL,
                            postal_code VARCHAR(20) DEFAULT NULL,
                            balance BIGINT DEFAULT 100,
                            version INT
);

-- 5. Table: pharmacists
CREATE TABLE pharmacists (
                             id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
                             name VARCHAR(255) NOT NULL UNIQUE,
                             email VARCHAR(255) NOT NULL UNIQUE,
                             pharmacy_id BIGINT NOT NULL,
                             is_admin

                              DEFAULT FALSE,
                             version INT,

                             CONSTRAINT fk_pharmacists_pharmacy FOREIGN KEY (pharmacy_id) REFERENCES pharmacies(id)
);
CREATE INDEX idx_pharmacists_pharmacy_id ON pharmacists(pharmacy_id);

-- 6. Table: orders
CREATE TABLE orders (
                        id BIGSERIAL PRIMARY KEY,
                        pharmacist_id UUID NOT NULL, -- Changed from BIGINT to UUID to match pharmacists.id
                        pharmacy_id BIGINT NOT NULL,
                        order_date TIMESTAMPTZ DEFAULT NOW(),
                        order_delivered_date TIMESTAMPTZ,
                        order_closed BOOLEAN DEFAULT FALSE,
                        version INT,

                        CONSTRAINT fk_orders_pharmacist FOREIGN KEY (pharmacist_id) REFERENCES pharmacists(id),
                        CONSTRAINT fk_orders_pharmacy FOREIGN KEY (pharmacy_id) REFERENCES pharmacies(id)
);
CREATE INDEX idx_orders_pharmacist_id ON orders(pharmacist_id);
CREATE INDEX idx_orders_pharmacy_id ON orders(pharmacy_id);

-- 7. Table: audit_logs
-- (Created after orders/medicines/batches due to FK dependencies)
CREATE TABLE audit_logs (
                            id BIGSERIAL PRIMARY KEY,
                            user_id UUID, -- Polymorphic relation (no strict FK enforced here usually, or conditional)
                            user_type VARCHAR(255),
                            med_id BIGINT,
                            batch_id BIGINT,
                            action VARCHAR(255),
                            batch_change_details JSONB NOT NULL,
                            initial_batch_state BIGINT,
                            current_batch_state BIGINT,
                            order_id BIGINT,
                            version INT,

                            CONSTRAINT fk_audit_medicines FOREIGN KEY (med_id) REFERENCES medicines(id),
                            CONSTRAINT fk_audit_batches FOREIGN KEY (batch_id) REFERENCES med_batches(id),
                            CONSTRAINT fk_audit_orders FOREIGN KEY (order_id) REFERENCES orders(id)
);
CREATE INDEX idx_audit_logs_med_id ON audit_logs(med_id);
CREATE INDEX idx_audit_logs_batch_id ON audit_logs(batch_id);

-- 8. Table: order_list
CREATE TABLE order_list (
                            id BIGSERIAL PRIMARY KEY,
                            medicines_id BIGINT NOT NULL,
                            order_id BIGINT NOT NULL,
                            amount_ordered INT,
                            version INT,

                            CONSTRAINT fk_order_list_medicines FOREIGN KEY (medicines_id) REFERENCES medicines(id),
                            CONSTRAINT fk_order_list_orders FOREIGN KEY (order_id) REFERENCES orders(id),
                            CONSTRAINT uq_order_list_med_order UNIQUE (medicines_id, order_id)
);
CREATE INDEX idx_order_list_medicines_id ON order_list(medicines_id);
CREATE INDEX idx_order_list_order_id ON order_list(order_id);

-- 9. Table: ratings (Fixed typo "ratigs")
CREATE TABLE ratings (
                         id BIGSERIAL PRIMARY KEY,
                         pharmacist_id UUID NOT NULL, -- Changed from BIGINT to UUID
                         pharmacy_id BIGINT NOT NULL,
                         medicines_id BIGINT NOT NULL,
                         rating INT NOT NULL,

                         CONSTRAINT fk_ratings_pharmacist FOREIGN KEY (pharmacist_id) REFERENCES pharmacists(id),
                         CONSTRAINT fk_ratings_pharmacy FOREIGN KEY (pharmacy_id) REFERENCES pharmacies(id),
                         CONSTRAINT fk_ratings_medicines FOREIGN KEY (medicines_id) REFERENCES medicines(id)
);
CREATE INDEX idx_ratings_pharmacist_id ON ratings(pharmacist_id);
CREATE INDEX idx_ratings_pharmacy_id ON ratings(pharmacy_id);
CREATE INDEX idx_ratings_medicines_id ON ratings(medicines_id);

-- 10. Table: reviews
CREATE TABLE reviews (
                         id BIGSERIAL PRIMARY KEY,
                         pharmacist_id UUID NOT NULL, -- Changed from BIGINT to UUID
                         pharmacy_id BIGINT NOT NULL,
                         medicines_id BIGINT NOT NULL,
                         rate_id BIGINT,
                         parent_review_id BIGINT,
                         review TEXT NOT NULL, -- Changed TXT to TEXT

                         CONSTRAINT fk_reviews_pharmacist FOREIGN KEY (pharmacist_id) REFERENCES pharmacists(id),
                         CONSTRAINT fk_reviews_pharmacy FOREIGN KEY (pharmacy_id) REFERENCES pharmacies(id),
                         CONSTRAINT fk_reviews_medicines FOREIGN KEY (medicines_id) REFERENCES medicines(id),
                         CONSTRAINT fk_reviews_ratings FOREIGN KEY (rate_id) REFERENCES ratings(id),
                         CONSTRAINT fk_reviews_parent FOREIGN KEY (parent_review_id) REFERENCES reviews(id),

    -- Logic: Must have either rate_id OR parent_review_id, but NOT both.
                         CONSTRAINT must_have_to_rate_or_have_parent_not_both
                             CHECK (
                                 ((rate_id IS NOT NULL) OR (parent_review_id IS NOT NULL))
                                     AND
                                 ((rate_id IS NULL) OR (parent_review_id IS NULL))
                                 )
);
CREATE INDEX idx_reviews_pharmacist_id ON reviews(pharmacist_id);
CREATE INDEX idx_reviews_pharmacy_id ON reviews(pharmacy_id);
CREATE INDEX idx_reviews_medicines_id ON reviews(medicines_id);
CREATE INDEX idx_reviews_rate_id ON reviews(rate_id);
CREATE TABLE first_mock_users (
                                  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    -- add other columns that your Java entity expects
                                  name VARCHAR(255)
);