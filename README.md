### Project Documentation "Task Manager"

#### General Overview
"Task Manager" is a mobile application for Android, designed for efficient task management and reminding users about important events or tasks through notifications. The application is developed using modern technologies and libraries, ensuring reliability and ease of use.

#### Technical Details
- **Minimum and Target SDK Versions**:
  - Minimum SDK version: 24 (Android 7.0 Nougat).
  - Target SDK version: 34.

- **Key Dependencies**:
  - `androidx.core:core-ktx`: Kotlin extensions for core Android libraries.
  - `androidx.appcompat:appcompat`: Library for ensuring compatibility across different versions of Android.
  - `com.google.android.material:material`: Material Design library for interface styling.
  - `androidx.constraintlayout:constraintlayout`: Library for flexible placement of UI elements.

- **Permissions**:
  - `android.permission.POST_NOTIFICATIONS`: Permission to send notifications in Android 12 and above.
  - `com.android.alarm.permission.SET_ALARM`: Permission to set alarms.

#### Functionality
- **Task Reminders**:
  The application allows users to set reminders for specific tasks. These reminders can include text descriptions and a time for the notification.

- **Notifications**:
  When it's time to perform a task, the app sends a notification, helping users remember important tasks.

- **Notification Management**:
  The app uses `AlarmManager` to schedule notifications and `BroadcastReceiver` to handle and display them.

#### User Interface
- **View Binding**:
  Using View Binding for simple and secure access to interface elements.

- **Design and Style**:
  The application uses a modern visual style, thanks to the Material Design library, ensuring convenience and attractiveness in interacting with the app.
