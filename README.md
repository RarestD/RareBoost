
# 🌟 RareBoost – Discord Automated Messaging Tool

RareBoost is a JavaFX desktop app for automating Discord messages via Discord API. Users can manage multiple tasks, customize messages and delays, and control them with a modern GUI. multithreading, and real-time status, it's ideal for efficient Discord bot automation.

## 🚀 Overview
RareBoost empowers users to manage and execute automated message-sending tasks to Discord via a GUI. Users can log in with a token, manage multiple tasks, configure message content, and control delays — all within a modern, responsive interface.

## ✨ Features
- 🎨 Intuitive GUI built with JavaFX and FXML
- ⚙️ Multi-task System: Create, edit, start, and stop individual or multiple message tasks (Constellations)
- ⏱️ Custom Delay Settings per task
- 📊 Real-time Status Monitoring
- 💾 Task Configuration Persistence
- 🧵 Uses Multithreading for smooth and responsive task execution
- 📚 Clean code architecture: separated controllers, services, entities, and views

## 🏗️ Tech Stack
| Technology       | Description                              |
|------------------|------------------------------------------|
| Java 11+         | Core programming language                |
| JavaFX 17+       | GUI framework with FXML                  |
| Spring           | To send request to discord API           |
| Maven            | Build and dependency management          |
| MaterialFX       | UI Component Library for JavaFX          |
| IntelliJ IDEA    | IDE for development                      |

## 📂 Project Structure Highlights
```
RareBoost/
├── src/
│   └── main/
│       ├── java/
│       │   └── rare/creations/RareBoost/
│       │       ├── controllers/       # GUI controllers
│       │       ├── entity/            # Data models and user info
│       │       ├── service/           # Task management and request services
│       │       ├── view/              # GUI views and handlers
│       │       └── RareBoostApplication.java
│       └── resources/
│           ├── fxml/                  # GUI layout files
│           ├── css/                   # Stylesheets
│           ├── fonts/                 # Custom fonts
│           └── application.properties
```

## 🧑‍💻 How to Run
### Prerequisites
- Java 11+
- Maven

### Steps
1. Clone the repo:
   ```bash
   git clone https://github.com/RarestD/RareBoost.git
   cd RareBoost
   ```

2. Build and run the project:
   ```bash
   mvn clean install
   mvn javafx:run
   ```

3. Enter your Discord user token, channel ID, and delay.
4. Alternatively you can run the .jar in the target folder


## Run Executor
### Prerequisites
- Java 11+
- Maven

### Details
This folder github only for the logic and if you want to use the APP you can download the jar from here
[JAR FILE](https://drive.google.com/drive/folders/1B0TF6yykC6gTTi7mL-VMAilOo-fL4YOU?usp=sharing)
download the jar and run it

## 🛠️ Future Improvements
- Cloud-based configuration sync
- Scheduled messaging (cron-style)
- Rate-limit handling and analytics
- Multi-user support

## 💼 Value to Employers (HRD)
This project demonstrates:
- Full-cycle software development using Java ecosystem
- Ability to create responsive GUI and handle complex workflows
- Experience with API integration, data persistence, and multithreading
- Understanding of clean architecture and modular design
- Initiative in building practical and relevant tools

## 👨‍💻 Author
- Nama: Darren Ardhianata
- GitHub: [RarestD](https://github.com/RarestD)
- LinkedIn: [LinkedIn](https://www.linkedin.com/in/darren-ardhianata-889b97226)
- Email: [ardiandarren6@gmail.com]
