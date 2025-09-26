import React, { useEffect } from 'react';
import { useTaskStore } from '../../modules/tasks/stores/taskStore';
import { useAuthStore } from '../../modules/auth/stores/authStore';
import { demoTasks, demoUser } from '../../data/demoData';
import toast from 'react-hot-toast';

export const DemoLoader: React.FC = () => {
  const { tasks, isLoading } = useTaskStore();
  const { user, isAuthenticated } = useAuthStore();

  useEffect(() => {
    // Загружаем демо данные только если пользователь авторизован и нет задач
    if (isAuthenticated && user && tasks.length === 0 && !isLoading) {
      // Имитируем загрузку данных
      const loadDemoData = async () => {
        try {
          // Симуляция загрузки
          await new Promise(resolve => setTimeout(resolve, 1000));
          
          // Загружаем демо задачи
          useTaskStore.setState({ tasks: demoTasks });
          
          toast.success('Демо данные загружены!');
        } catch (error) {
          toast.error('Ошибка загрузки демо данных');
        }
      };

      loadDemoData();
    }
  }, [isAuthenticated, user, tasks.length, isLoading]);

  return null; // Этот компонент не рендерит UI
};
