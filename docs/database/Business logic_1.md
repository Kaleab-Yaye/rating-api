# Content

This documentation is to formally describe the initial picture of the database for our **Pharmacy API**.  
This is not the final documentation but the first of many to come.

---

# Business Logic

Our guiding philosophy is that the database should mirror the real-world company it is meant to serve.  
With that in mind, here is what the database should look like:

The company we are building this API for is a **medicine importing company** that distributes medicine to different pharmacies.  
Therefore, `Medicines` are the central theme here.

Medicines are **data-sensitive**, meaning a single pharmacy can have two of the same medicines with different expiration dates.  
We need to accommodate this by creating a **virtual entity** that references the primary key of the `Medicines` table.  
This entity will include:
- A number (to indicate the number of batches that exist)
- An expiration date

The application layer will calculate the total number of medicines available across all batches that are not expired, instead of storing the calculated value directly.

---

### Restocking and Logging

There must be a person or a group of people responsible for restocking medicines, tracking low stock, and adding new medicines.  
A mechanism should ensure that when restocking happens, the same medicine does not exist as a separate entity.

Any change made by this group must be logged in the database.  
We will enforce this update-and-log relationship at the application layer by making these two transactions part of one big `@transaction` process.

---

### Pharmacies and Pharmacists

Medicines are bought by `Pharmacies`, not individuals.  
Therefore, there needs to exist a standalone entity representing pharmacies.

Pharmacies usually have more than one worker, in our case, these are `Pharmacists`.  
Pharmacists must be directly linked to the pharmacies they work in.  
This mirrors accountability and helps answer **“who did what”** under the name of the pharmacy.

The same update log used for inventory managers will also be used here, but by default, the reason for the update will be **“Purchased.”**

---

### Orders

`Pharmacies order Medicines`, so an **Order** needs to be represented in our database.

**Who can order?** Pharmacies.  
Therefore, an order must be associated with a pharmacy, and an order not made by a pharmacy should not exist.

A pharmacy can order multiple items at once.  
Since putting all of that inside the `Order` entity would violate atomicity, we need a **helper conjunctional entity** that links `Order` to `Medicines` and specifies the quantity.

Because this conjunctional table does more than just link a medicine to an order, it will:
- Have a surrogate key for identification
- Store whether an order is delivered

We must ensure no two entities in this conjunctional table have the same order number and medicine ID.

> For our MVP, we are leaving out the delivery system.

---

### Payment

Another important integration we need is **Payment**.  
Integrating our MVP with a real payment infrastructure is legally complex.  
For our production-ready service, this will be non-negotiable.  
However, for now, we will emulate it using a **nonexistent virtual currency** (as numeric values).

There will be a **conjunctional table** that links an order to the initial and final state of the currency balance for a given pharmacy.  
Currency integrity will be enforced at the application layer.

---

### User and Audit Requirements

Due to the need for audit logs, all users in our database, whether they are pharmacy personnel or staff , should have a `UUID` that our application will handle for assigning IDs.
