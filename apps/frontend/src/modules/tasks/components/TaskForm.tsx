import React, { useState } from 'react';
import { Plus } from 'lucide-react';
import { useTaskStore } from '../stores/taskStore';
import toast from 'react-hot-toast';

export const TaskForm: React.FC = () => {
  const [title, setTitle] = useState('');
  const { createTask, isLoading } = useTaskStore();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    
    if (!title.trim()) {
      toast.error('Введите заголовок задачи');
      return;
    }

    try {
      await createTask(title.trim(), '');
      setTitle('');
      toast.success('Задача создана!');
    } catch (error) {
      // Ошибка уже обработана в store
    }
  };

  return (
    <div className="card">
      <form onSubmit={handleSubmit} className="flex gap-2">
        <input
          type="text"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
          placeholder="Введите заголовок новой задачи..."
          className="input flex-1"
          disabled={isLoading}
        />
        <button
          type="submit"
          disabled={isLoading || !title.trim()}
          className="btn-primary px-4 py-2"
        >
          <Plus size={16} className="mr-1" />
          {isLoading ? 'Добавление...' : 'Добавить'}
        </button>
      </form>
    </div>
  );
};
