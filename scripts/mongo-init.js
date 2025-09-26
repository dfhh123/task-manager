// MongoDB initialization script for Super Pet Event Platform
// This script sets up the initial database and collections

// Switch to the taskdb database
db = db.getSiblingDB('taskdb');

// Create a user for the application
db.createUser({
  user: 'taskuser',
  pwd: 'taskpassword',
  roles: [
    {
      role: 'readWrite',
      db: 'taskdb'
    }
  ]
});

// Create collections with initial indexes
db.createCollection('tasks');

// Create indexes for better performance
db.tasks.createIndex({ "title": 1 });
db.tasks.createIndex({ "status": 1 });
db.tasks.createIndex({ "priority": 1 });
db.tasks.createIndex({ "assignedTo": 1 });
db.tasks.createIndex({ "createdAt": 1 });
db.tasks.createIndex({ "dueDate": 1 });
db.tasks.createIndex({ "linkedUsers": 1 });

// Create compound indexes for common queries
db.tasks.createIndex({ "status": 1, "priority": 1 });
db.tasks.createIndex({ "status": 1, "createdAt": 1 });
db.tasks.createIndex({ "assignedTo": 1, "status": 1 });

// Create text index for full-text search
db.tasks.createIndex({ 
  "title": "text", 
  "description": "text" 
});

// Insert some sample data for development
db.tasks.insertMany([
  {
    title: "Implement user authentication",
    description: "Implement JWT-based authentication system with role-based access control",
    status: "PENDING",
    priority: "HIGH",
    linkedUsers: [],
    assignedTo: null,
    dueDate: new Date(Date.now() + 7 * 24 * 60 * 60 * 1000), // 7 days from now
    createdAt: new Date(),
    updatedAt: new Date()
  },
  {
    title: "Setup monitoring dashboard",
    description: "Configure Prometheus and Grafana for application monitoring",
    status: "IN_PROGRESS",
    priority: "MEDIUM",
    linkedUsers: [],
    assignedTo: null,
    dueDate: new Date(Date.now() + 14 * 24 * 60 * 60 * 1000), // 14 days from now
    createdAt: new Date(),
    updatedAt: new Date()
  },
  {
    title: "Write API documentation",
    description: "Create comprehensive API documentation using OpenAPI/Swagger",
    status: "COMPLETED",
    priority: "LOW",
    linkedUsers: [],
    assignedTo: null,
    dueDate: new Date(Date.now() - 3 * 24 * 60 * 60 * 1000), // 3 days ago
    createdAt: new Date(Date.now() - 10 * 24 * 60 * 60 * 1000), // 10 days ago
    updatedAt: new Date(Date.now() - 3 * 24 * 60 * 60 * 1000) // 3 days ago
  }
]);

print('MongoDB initialization completed successfully!');
print('Database: taskdb');
print('User: taskuser');
print('Collections: tasks');
print('Sample data inserted: 3 tasks');
