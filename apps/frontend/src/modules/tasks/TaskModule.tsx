import React from 'react';
import { useTaskStore } from './stores/taskStore';
import { TaskForm } from './components/TaskForm';
import { TaskStats } from './components/TaskStats';
import { TaskList } from './components/TaskList';

export const TaskModule: React.FC = () => {
  const { getCompletedTasks, getPendingTasks } = useTaskStore();
  
  const completedTasks = getCompletedTasks();
  const pendingTasks = getPendingTasks();

  return (
    <div className="max-w-4xl mx-auto p-6">
      <div className="mb-8">
        <h1 className="text-3xl font-bold text-gray-900 mb-2">
          Планировщик задач
        </h1>
        <p className="text-gray-600">
          Управляйте своими задачами эффективно
        </p>
      </div>

      <TaskStats />

      <div className="space-y-6">
        <TaskForm />

        <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
          <TaskList
            tasks={pendingTasks}
            title="Активные задачи"
            emptyMessage="Нет активных задач. Добавьте новую задачу выше!"
          />
          
          <TaskList
            tasks={completedTasks}
            title="Выполненные задачи"
            emptyMessage="Нет выполненных задач"
          />
        </div>
      </div>
    </div>
  );
};

// Экспорт для использования в других модулях
export { useTaskStore } from './stores/taskStore';
export type { Task } from './stores/taskStore';
