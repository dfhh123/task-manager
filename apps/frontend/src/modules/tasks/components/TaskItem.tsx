import React, { useState } from 'react';
import { Check } from 'lucide-react';
import { Task } from '../stores/taskStore';
import { useTaskStore } from '../stores/taskStore';
import { TaskModal } from './TaskModal';

interface TaskItemProps {
  task: Task;
}

export const TaskItem: React.FC<TaskItemProps> = ({ task }) => {
  const { toggleTask } = useTaskStore();
  const [isModalOpen, setIsModalOpen] = useState(false);

  const handleToggle = async () => {
    await toggleTask(task.id);
  };

  const handleClick = () => {
    setIsModalOpen(true);
  };

  return (
    <>
      <div 
        className={`card cursor-pointer transition-all hover:shadow-md ${
          task.completed ? 'bg-green-50 border-green-200' : 'bg-white'
        }`}
        onClick={handleClick}
      >
        <div className="flex items-start gap-3">
          <button
            onClick={(e) => {
              e.stopPropagation();
              handleToggle();
            }}
            className={`mt-1 w-5 h-5 rounded border-2 flex items-center justify-center transition-colors ${
              task.completed
                ? 'bg-green-500 border-green-500 text-white'
                : 'border-gray-300 hover:border-green-400'
            }`}
          >
            {task.completed && <Check size={12} />}
          </button>
          
          <div className="flex-1 min-w-0">
            <h3 className={`font-medium ${
              task.completed ? 'text-green-700 line-through' : 'text-gray-900'
            }`}>
              {task.title}
            </h3>
            {task.description && (
              <p className={`text-sm mt-1 ${
                task.completed ? 'text-green-600' : 'text-gray-600'
              }`}>
                {task.description}
              </p>
            )}
            <p className="text-xs text-gray-400 mt-2">
              Создано: {new Date(task.createdAt).toLocaleDateString('ru-RU')}
            </p>
          </div>
        </div>
      </div>

      <TaskModal
        task={task}
        isOpen={isModalOpen}
        onClose={() => setIsModalOpen(false)}
      />
    </>
  );
};
