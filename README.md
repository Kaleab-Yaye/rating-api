**The following steps assume that you forked the repo and opened it in your preferred IDE (IntelliJ preferably)!**

---

# What is Maven?

Maven is like a dependency manager, and we will use it to manage the dependencies that we want, without having to worry about how to acquire or download the dependencies.  
`pom.xml` is where all the dependencies are defined.

<img width="569" height="589" alt="image" src="https://github.com/user-attachments/assets/840bae48-ea8a-43db-82bf-637f0e4a0551" />

Since I have already handled the very basic dependencies, what you need to do is go to the terminal and run:

```bash
./mvnw clean install
```

Now your forked project is up to speed!

---

# What is Spotless?

Spotless is not a dependency but rather an annotation-enabled Maven plugin that solves line break issues (LF, CLRF) and indentation conflicts (tabs vs spaces).  
I have set this plugin to treat a tab as 4 spaces.

Before you commit any of your work, run:

```bash
./mvnw spotless:apply
```

Otherwise, the tests won’t pass and the pre-hook I wrote won’t let you commit.  
This keeps the project clean.

<img width="1159" height="637" alt="image" src="https://github.com/user-attachments/assets/d2271b30-05f4-4ac9-91eb-ed5bcae63a5d" />

---

# What is a Git Pre-hook?

We will update this based on our needs, but what it basically does now is: when one of us tries to commit, this pre-hook script will run and do two things:

1. It will check if the Spotless check passes.  
2. It will run `./mvnw test`.

If any of those two return `1` (error exit code), the commit will be rolled back.

What you need to do is just run this command once:

```bash
git config core.hooksPath .githooks
```

And you are all set.

---

# What is a Container?

Instead of deploying our database server in the wild, we will use a container so that everyone works with the same database setup.  
For this, we will use **Docker**.

First, download Docker Desktop from:  
[https://docs.docker.com/desktop/setup/install/windows-install/](https://docs.docker.com/desktop/setup/install/windows-install/)

Then, once you have Docker Desktop running, open your terminal and run:

```bash
docker-compose up -d
```

Docker Compose uses the `.yml` file that contains the Docker image configuration we need to work on.

<img width="1166" height="428" alt="image" src="https://github.com/user-attachments/assets/806e2675-4956-4f20-818d-191443297b3d" />

Don’t forget to change the password.

Make sure you see exactly this on your Docker dashboard:

<img width="1529" height="167" alt="image" src="https://github.com/user-attachments/assets/21f85e5c-4390-4ee0-9c27-cf49c91a4c2e" />

---

That’s basically it to get you started!  
There are things like **active profiles** and the **tests I wrote** to check the database, but we can talk about that later.  
This was just to get you up to speed with using the main tools.  
Our development phase will depend on these setups.
