import React from 'react';
import { TaskItem } from './TaskItem';
import { Task } from '../stores/taskStore';

interface TaskListProps {
  tasks: Task[];
  title: string;
  emptyMessage: string;
}

export const TaskList: React.FC<TaskListProps> = ({ tasks, title, emptyMessage }) => {
  if (tasks.length === 0) {
    return (
      <div className="card text-center py-8">
        <p className="text-gray-500">{emptyMessage}</p>
      </div>
    );
  }

  return (
    <div className="space-y-3">
      <h3 className="text-lg font-semibold text-gray-800">{title}</h3>
      <div className="space-y-2">
        {tasks.map((task) => (
          <TaskItem key={task.id} task={task} />
        ))}
      </div>
    </div>
  );
};
