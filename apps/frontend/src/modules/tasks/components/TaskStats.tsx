import React from 'react';
import { CheckCircle, Clock, BarChart3 } from 'lucide-react';
import { useTaskStore } from '../stores/taskStore';

export const TaskStats: React.FC = () => {
  const stats = useTaskStore(state => state.getTasksStats());

  return (
    <div className="grid grid-cols-1 md:grid-cols-3 gap-4 mb-6">
      <div className="card">
        <div className="flex items-center gap-3">
          <div className="p-2 bg-green-100 rounded-lg">
            <CheckCircle className="text-green-600" size={20} />
          </div>
          <div>
            <p className="text-sm font-medium text-gray-600">Выполнено</p>
            <p className="text-2xl font-bold text-green-600">{stats.completed}</p>
          </div>
        </div>
      </div>

      <div className="card">
        <div className="flex items-center gap-3">
          <div className="p-2 bg-yellow-100 rounded-lg">
            <Clock className="text-yellow-600" size={20} />
          </div>
          <div>
            <p className="text-sm font-medium text-gray-600">В процессе</p>
            <p className="text-2xl font-bold text-yellow-600">{stats.pending}</p>
          </div>
        </div>
      </div>

      <div className="card">
        <div className="flex items-center gap-3">
          <div className="p-2 bg-blue-100 rounded-lg">
            <BarChart3 className="text-blue-600" size={20} />
          </div>
          <div>
            <p className="text-sm font-medium text-gray-600">Всего</p>
            <p className="text-2xl font-bold text-blue-600">{stats.total}</p>
          </div>
        </div>
      </div>
    </div>
  );
};
