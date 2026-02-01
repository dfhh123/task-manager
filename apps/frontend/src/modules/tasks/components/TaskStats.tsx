import React, { useMemo } from 'react';
import { CheckCircle, Clock, BarChart3 } from 'lucide-react';
import { useTaskStore } from '../stores/taskStore';

export const TaskStats: React.FC = () => {
  const tasks = useTaskStore(state => state.tasks);
  
  const stats = useMemo(() => {
    const completed = tasks.filter(task => task.completed).length;
    const pending = tasks.filter(task => !task.completed).length;
    return { completed, pending, total: tasks.length };
  }, [tasks]);

  return (
    <div className="grid grid-cols-1 md:grid-cols-3 gap-4 mb-6">
      <div className="card">
        <div className="flex items-center gap-3">
          <div className="p-2 bg-green-100 dark:bg-green-900/30 rounded-lg">
            <CheckCircle className="text-green-600 dark:text-green-400" size={20} />
          </div>
          <div>
            <p className="text-sm font-medium text-gray-600 dark:text-gray-400">Выполнено</p>
            <p className="text-2xl font-bold text-green-600 dark:text-green-400">{stats.completed}</p>
          </div>
        </div>
      </div>

      <div className="card">
        <div className="flex items-center gap-3">
          <div className="p-2 bg-yellow-100 dark:bg-yellow-900/30 rounded-lg">
            <Clock className="text-yellow-600 dark:text-yellow-400" size={20} />
          </div>
          <div>
            <p className="text-sm font-medium text-gray-600 dark:text-gray-400">В процессе</p>
            <p className="text-2xl font-bold text-yellow-600 dark:text-yellow-400">{stats.pending}</p>
          </div>
        </div>
      </div>

      <div className="card">
        <div className="flex items-center gap-3">
          <div className="p-2 bg-blue-100 dark:bg-blue-900/30 rounded-lg">
            <BarChart3 className="text-blue-600 dark:text-blue-400" size={20} />
          </div>
          <div>
            <p className="text-sm font-medium text-gray-600 dark:text-gray-400">Всего</p>
            <p className="text-2xl font-bold text-blue-600 dark:text-blue-400">{stats.total}</p>
          </div>
        </div>
      </div>
    </div>
  );
};
