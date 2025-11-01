# Content

This documentation will explain the business logic for our real-world company at a **database entity** level.

---

## Entities

**All entities will have a `version` attribute**, so that every action taken by the application layer respects **optimistic locking** to avoid race conditions.

---

### Medicines

This entity (table) is where all the medicines that the company holds exist.  
This table needs the following attributes:

1. **ID** — Must be assigned by the database; it is also the primary key.  
2. **Name** — The name of the medicine.  
   - Should be indexed for when users search the database by name.  
   - Must be unique.  
   - Must not be null.  
3. **About** — A plain text column that describes what the medicine is.  
4. **Price** — The cost of the medicine.

---

### med_batches

This virtual entity solves the issue with expiration that we discussed.

1. **ID** — A database-handled surrogate primary key.  
2. **med_id** — A foreign key referencing the `medicines` table’s primary key. Must be indexed.  
3. **amount_present** — The number of medicines in that batch.  
4. **expiry** — Since most queries for this table depend on expiration date, this column must also be indexed.

---

### inventory_managers

These are people responsible for ensuring the database stock reflects the real-world inventory.  
Our application logic should allow them to edit the number for existing entries and insert new ones.

They must have the following attributes:

1. **id** — UUID handled by the application layer; it is the primary key.  
2. **name, email address, and other personal identification columns**.  
3. **is_admin** — A boolean column defining who the admin of these inventory managers is, so that they can add as many inventory managers as necessary.

---

### audit_logs

This is a **conjunctional table** with a surrogate key that links any change happening to the `medicines` table and the `med_batches` table.

Since this table logs history, calculating the number of all batches belonging to a medicine is not a bad practice and makes logic easier.  
Instead of saying *“Abebe inserted 100 meds of batch id XXXX”* or *“medicine id YYYY”*, which is technically fine but complicates logic,  
it’s clearer to say *“Abebe inserted 100 of the medicine type YYYY.”*

However, knowing which medicine changed may not be enough to explain what happened.  
The real change occurs at the **batch level**, so we need to relate the audit log to batches.

This entity will have the following attributes:

1. **id** — Primary key handled by the database.  
2. **user_id** — UUID. This is a *polymorphic relationship* not enforced by a foreign key,  
   so we also need a field to specify the type:  
   - **user_type** — Defines the type of user.  
3. **med_id** — Foreign key; must be indexed.  
4. **batch_id** — Foreign key; must be indexed.  
5. **initial_batch_state** — Shows what the batch looked like before the change.  
6. **current_batch_state** — Shows what the batch looks like after the change.  
7. **batch_change_details** — Data type `JSONB`, storing a JSON representation of the before-and-after states of the change.  
8. **order_id** — A foreign key relating this table to an order.  
   This must be optional so that staff-related changes can have this column as null.

---

### pharmacies

This is the central entity shared by other entities that can make orders.  
The following are its attributes:

1. **id** — Primary key, assigned by the database.  
2. **name** — Legal name of the pharmacy.  
3. **street_address** — Not null.  
4. **city**  
5. **state**  
6. **postal_code** — Can be null (outdated).  
7. **country**  
8. **balance** — The money that the pharmacy has recharged into their wallet.

---

### pharmacists

These are entities that can place orders, and they are **children of a pharmacy**.  
The following are their attributes:

1. **id** — UUID handled by the application layer.  
2. **name** — Must be unique.  
3. **email** — Must be unique.  
4. **pharmacy_id** — Foreign key indicating which pharmacy they work for.  
5. **is_admin** — Indicates who is the manager of the pharmacy.  
   This makes it easier when they want to add another pharmacist.

---

### orders

As stated earlier, pharmacists place orders.  
However, a single order can contain many medicines.  
Handling orders separately from ordered materials prevents complexity and maintains atomicity.

To avoid performing unnecessary join operations (e.g., answering questions like *“What are all the orders made by pharmacy X?”*),  
we add a **pharmacy foreign key** to this table.

The following are the attributes this virtual entity should have:

1. **id** — Primary key, assigned by the database.  
2. **pharmacist_id** — Foreign key directly relating the order to the pharmacist who placed it. Must be indexed.  
3. **order_date** — The date when the order was placed.  
4. **order_delivered_date** — The date when the order was delivered.  
5. **order_closed** — When the user verifies they have received the order, the order is closed, and no further operation can be done on this record.  
6. **pharmacy_id** — Foreign key directly relating the order to the pharmacy.

---

#### order_list

This is the **conjunctional table** that relates an order to the list of medicines that are ordered.  
It will have the following attributes:

1. **id** — Must be assigned by the database.  
2. **medicine_id** — Foreign key referencing the `medicines` table.  
3. **order_id** — Foreign key referencing the `orders` table.  
   - To ensure that no two rows have the same order ID and medicine ID, the combination of these two foreign keys must be unique.  
4. **amount_ordered** — The number of medicines ordered for that medicine type.
