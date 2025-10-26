db = db.getSiblingDB('taskdb');

db.createUser({
  user: 'admin',
  pwd: 'admin',
  roles: [{ role: 'readWrite', db: 'taskdb' }]
});

db.createCollection('tasks');
db.tasks.createIndex({ "status": 1, "priority": 1 });
db.tasks.createIndex({ "assignedTo": 1, "status": 1 });
db.tasks.createIndex({ "title": "text", "description": "text" });

print('MongoDB initialized');
