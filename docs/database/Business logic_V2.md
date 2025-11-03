# Add Rating Logic

We need to add the following features as per the company’s new requirements:

Products must be rated out of **five stars**, and pharmacies should be able to give both **ratings and comments**.  
Comments should also be **nested** and **threaded**.

Here are the required changes:

- The **medicine entity** will need a way to store the **calculated average** of all user ratings.  
- We also need a way to **associate individual ratings** with their respective medicines.  
- Reviews should be displayed clearly, and other users should be able to **comment on open reviews**.  
- A person can **rate without leaving a review**, but they **cannot leave a review without first rating**.  
- However, to **comment on an existing review**, a user does **not** need to have left a review themselves.
