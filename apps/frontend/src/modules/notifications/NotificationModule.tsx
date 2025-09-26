import React, { useEffect } from 'react';
import { useNotificationStore } from './stores/notificationStore';
import { useAuthStore } from '../auth/stores/authStore';
import { NotificationSettings } from './components/NotificationSettings';

export const NotificationModule: React.FC = () => {
  const { sendWelcomeEmail, checkDailyReport } = useNotificationStore();
  const { user, isAuthenticated } = useAuthStore();

  // Отправляем приветственное письмо при авторизации
  useEffect(() => {
    if (isAuthenticated && user) {
      sendWelcomeEmail();
    }
  }, [isAuthenticated, user, sendWelcomeEmail]);

  // Настраиваем ежедневные отчеты
  useEffect(() => {
    if (isAuthenticated) {
      checkDailyReport();
    }
  }, [isAuthenticated, checkDailyReport]);

  return (
    <div className="max-w-2xl mx-auto p-6">
      <div className="mb-6">
        <h2 className="text-2xl font-bold text-gray-900 mb-2">
          Уведомления
        </h2>
        <p className="text-gray-600">
          Настройте уведомления по email
        </p>
      </div>

      <NotificationSettings />
    </div>
  );
};

// Экспорт для использования в других модулях
export { useNotificationStore } from './stores/notificationStore';
export type { NotificationSettings } from './stores/notificationStore';
