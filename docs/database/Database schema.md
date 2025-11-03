#### Med Entity Update

To accommodate the new requirement, an **average_rating** attribute will be included.

1. **ID** — Must be assigned by the database; it is also the primary key.  
2. **Name** — The name of the medicine.  
   - Should be indexed for when users search the database by name.  
   - Must be unique.  
   - Must not be null.  
3. **About** — A plain text column that describes what the medicine is.  
4. **Price** — The cost of the medicine.  
5. **average_rating** — The application layer should calculate and populate this value.

---

### ratings

This is a virtual table where all the ratings for a given medicine are stored.  
Here are the attributes of this entity:

1. **id** — Primary key, handled by the database.  
2. **pharmacy_id** — Indexed foreign key referencing the primary key of the `pharmacies` table.  
3. **pharmacist_id** — Indexed foreign key referencing the primary key of the `pharmacists` table (the one who left the rating).  
4. **rating** — Cannot be null. Every record of this entity must include a rating, and the rating value should be capped at **5**.  
5. **medicine_id** — Foreign key referencing the medicine being rated.

---

### reviews

This is another virtual table created to allow users to leave comments or reviews under medicines.  

There are two ways records can join this entity:
- They can either have a rating of the medicine in the `ratings` table.  
- Or they can be a **child review** of a **parent review** (self-referencing relationship).

The following are the attributes of this entity:

1. **id** — Primary key.  
2. **pharmacy_id** — Foreign key.  
3. **pharmacist_id** — Foreign key.  
4. **medicine_id** — Foreign key.  
5. **rate_id** — Nullable foreign key, but must be **unique** to ensure that no rating record is associated with more than one review.  
6. **parent_review_id** — Optional foreign key (self-referencing). Can be null.  
7. **review** — The actual review text.

We need to add a **constraint** that ensures both `rate_id` and `parent_review_id` are not null at the same time, and only one of them is non-null.

---

The reason for separating **ratings** and **reviews** is to prevent potential persistence bugs that could occur if comments were nested under users who only rated but did not leave reviews.  
Relying on whether the `review` column in the `ratings` table is null to differentiate between a rating and a review could become messy.  
By keeping ratings separate, we ensure the **review logic** remains clear, maintainable, and consistent.
