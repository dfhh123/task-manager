import React, { useState, useEffect } from 'react';
import { X, Trash2 } from 'lucide-react';
import { Task } from '../stores/taskStore';
import { useTaskStore } from '../stores/taskStore';
import toast from 'react-hot-toast';

interface TaskModalProps {
  task: Task;
  isOpen: boolean;
  onClose: () => void;
}

export const TaskModal: React.FC<TaskModalProps> = ({ task, isOpen, onClose }) => {
  const { updateTask, deleteTask } = useTaskStore();
  const [title, setTitle] = useState(task.title);
  const [description, setDescription] = useState(task.description);
  const [completed, setCompleted] = useState(task.completed);

  // Обновляем локальное состояние при изменении задачи
  useEffect(() => {
    setTitle(task.title);
    setDescription(task.description);
    setCompleted(task.completed);
  }, [task]);

  // Автосохранение при изменении полей
  useEffect(() => {
    if (title !== task.title || description !== task.description) {
      const timeoutId = setTimeout(() => {
        updateTask(task.id, { title, description });
      }, 500);
      return () => clearTimeout(timeoutId);
    }
  }, [title, description, task.id, task.title, task.description, updateTask]);

  const handleToggleCompleted = async () => {
    const newCompleted = !completed;
    setCompleted(newCompleted);
    await updateTask(task.id, { completed: newCompleted });
  };

  const handleDelete = async () => {
    if (window.confirm('Вы уверены, что хотите удалить эту задачу?')) {
      await deleteTask(task.id);
      toast.success('Задача удалена');
      onClose();
    }
  };

  if (!isOpen) return null;

  return (
    <div className="modal-overlay">
      <div className="modal-content max-w-lg">
        <div className="flex justify-between items-center mb-4">
          <h2 className="text-xl font-semibold">Редактирование задачи</h2>
          <button
            onClick={onClose}
            className="text-gray-400 hover:text-gray-600"
          >
            <X size={20} />
          </button>
        </div>

        <div className="space-y-4">
          <div>
            <label htmlFor="task-title" className="block text-sm font-medium text-gray-700 mb-1">
              Заголовок
            </label>
            <input
              id="task-title"
              type="text"
              value={title}
              onChange={(e) => setTitle(e.target.value)}
              className="input w-full"
              placeholder="Введите заголовок задачи"
            />
          </div>

          <div>
            <label htmlFor="task-description" className="block text-sm font-medium text-gray-700 mb-1">
              Описание
            </label>
            <textarea
              id="task-description"
              value={description}
              onChange={(e) => setDescription(e.target.value)}
              className="input w-full min-h-[100px] resize-none"
              placeholder="Введите описание задачи"
            />
          </div>

          <div className="flex items-center gap-2">
            <input
              id="task-completed"
              type="checkbox"
              checked={completed}
              onChange={handleToggleCompleted}
              className="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 rounded focus:ring-blue-500"
            />
            <label htmlFor="task-completed" className="text-sm font-medium text-gray-700">
              Задача выполнена
            </label>
          </div>

          <div className="flex justify-between items-center pt-4 border-t">
            <button
              onClick={handleDelete}
              className="btn-danger flex items-center gap-2"
            >
              <Trash2 size={16} />
              Удалить
            </button>
            
            <div className="text-sm text-gray-500">
              <p>Создано: {new Date(task.createdAt).toLocaleString('ru-RU')}</p>
              {task.updatedAt !== task.createdAt && (
                <p>Изменено: {new Date(task.updatedAt).toLocaleString('ru-RU')}</p>
              )}
            </div>
          </div>
        </div>

        <div className="mt-4 p-3 bg-blue-50 border border-blue-200 rounded">
          <p className="text-sm text-blue-800">
            <strong>Автосохранение:</strong> Изменения сохраняются автоматически при редактировании полей.
          </p>
        </div>
      </div>
    </div>
  );
};
