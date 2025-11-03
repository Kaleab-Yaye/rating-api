<img width="1310" height="1026" alt="V2_Schema" src="https://github.com/user-attachments/assets/3daf9e97-2b8c-4ed9-b478-891815bee59b" />

**This is a clean representation of what the database schema should look like.  
This is not an SQL script.**

---

# medicines
1. id; **BIGSERIAL**, PK  
2. name; **VARCHAR(255)**, UNIQUE, NOT NULL  
3. about; **TEXT**  
4. price; **BIGINT**
5. average_rating; **INT**  
6. version

Constraint;
```sql
CONSTRAINT rate_cant_exede_5 CHECK(average_trating<=5)
```

# med_batches
1. id; **BIGSERIAL**, PK  
2. med_id; **BIGINT**, FK → medicines.id, INDEX  
3. amount_present; **BIGINT**  
4. expiry; **DATE**  
5. version  

---

# inventory_managers
1. id; **UUID**, PK  
2. name; **VARCHAR(255)**, UNIQUE, NOT NULL  
3. email; **VARCHAR(255)**, UNIQUE, NOT NULL  
4. is_admin; **BOOLEAN**  
5. version  

---

# audit_logs
1. id; **BIGSERIAL**, PK  
2. user_id; **UUID** — polymorphic relation  
3. user_type; **VARCHAR(255)**  
4. med_id; **BIGINT**, FK → medicines.id, INDEX  
5. batch_id; **BIGINT**, FK → med_batches.id, INDEX  
6. action; **VARCHAR(255)**  
7. batch_change_details; **JSONB**, NOT NULL  
8. initial_batch_state; **BIGINT**  
9. current_batch_state; **BIGINT**  
10. order_id; FK → orders.id  
11. version  

---

# pharmacies
1. id; **BIGSERIAL**, PK  
2. name; **VARCHAR(255)**, UNIQUE, NOT NULL  
3. street_address; **VARCHAR(255)**, NOT NULL  
4. city; **VARCHAR(100)**, NOT NULL  
5. region; **VARCHAR(100)**, NOT NULL  
6. postal_code; **VARCHAR(20)**, DEFAULT NULL  
7. balance; **BIGINT**, DEFAULT 100  
8. version  

---

# pharmacists
1. id; **UUID**, PK  
2. name; **VARCHAR(255)**, NOT NULL, UNIQUE  
3. email; **VARCHAR(255)**, NOT NULL, UNIQUE  
4. pharmacy_id; **BIGINT**, FK → pharmacies.id, INDEX, NOT NULL  
5. is_admin; **BOOLEAN**, DEFAULT FALSE  
6. version  

---

# orders
1. id; **BIGSERIAL**, PK  
2. pharmacist_id; **BIGINT**, FK → pharmacists.id, INDEX, NOT NULL  
3. pharmacy_id; **BIGINT**, FK → pharmacies.id, INDEX, NOT NULL  
4. order_date; **TIMESTAMPTZ**, DEFAULT NOW()  
5. order_delivered_date; **TIMESTAMPTZ**  
6. order_closed; **BOOLEAN**, DEFAULT FALSE  
7. version  

---

# order_list
1. id; **BIGSERIAL**, PK  
2. medicines_id; **BIGINT**, FK → medicines.id, INDEX, NOT NULL  
3. order_id; **BIGINT**, FK → orders.id, INDEX, NOT NULL  
4. amount_ordered; **INT**  
5. version  

**CONSTRAINT:** UNIQUE(medicines_id, order_id)


# ratigs

1. id; **BIGSERIAL**, pk
2. pharmacist_id; **BIGINT**, FK → pharmacists.id, INDEX, NOT NULL 
3.  pharmacy_id; **BIGINT**, FK → pharmacies.id, INDEX, NOT NULL
4.  medicines_id; **BIGINT**, FK → medicines.id, INDEX, NOT NULL
5.  rating; **INT**, NOT NULL

# reviews
1. id; **BIGSERIAL**, pk
2. pharmacist_id; **BIGINT**, FK → pharmacists.id, INDEX, NOT NULL
3. pharmacy_id; **BIGINT**, FK → pharmacies.id, INDEX, NOT NULL
4. medicines_id; **BIGINT**, FK → medicines.id, INDEX, NOT NULL
5. rate_id; **BIGINT**, FK → ratigs.id, INDEX
6. parent_review_id; **BIGINT**, FK → reviews.id;
7. review; **TXT**, NOT NULL

Constraint

```sql
CONSTRAINT must_have_to_rate_or_have_parent_not_both CHECK (((rate_id = NOT NULL) OR (parent_id = NOT NULL)) AND( ( rate_id = NULL ) OR ( parent_id = NULL)))
```
