# Changes

The problem that we faced was that on our first decision to make the users be situated in different tables, it created an issue with authorization, since there is no way for Spring to know if the user that was retrieved is a **Pharmacist** object or **Inventory Manager**, and also whether they are admin or not. Since we already have the column to show if they are admin or not, we need a column to tell whether they are a pharmacist or an inventory manager.

So here is the updated look of the two entities:

---

### inventory_managers

These are people responsible for ensuring the database stock reflects the real-world inventory.  
Our application logic should allow them to edit the number for existing entries and insert new ones.

They must have the following attributes:

1. **id** — UUID handled by the application layer; it is the primary key.  
2. **name, email address, and other personal identification columns**.  
3. **is_admin** — A boolean column defining who the admin of these inventory managers is, so that they can add as many inventory managers as necessary.
4. **type** — A string column holding the type of user. For this table, the default value will be `"InventoryManager"`.

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
6. **type** — A string column holding the type of user. For this table, the default value will be `"Pharmacist"`.
