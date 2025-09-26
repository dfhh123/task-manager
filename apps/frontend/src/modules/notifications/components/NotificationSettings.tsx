import React from 'react';
import { Mail, Bell, Settings } from 'lucide-react';
import { useNotificationStore } from '../stores/notificationStore';
import { useAuthStore } from '../../auth/stores/authStore';

export const NotificationSettings: React.FC = () => {
  const { settings, updateSettings } = useNotificationStore();
  const { user } = useAuthStore();

  React.useEffect(() => {
    if (user?.email) {
      updateSettings({ email: user.email });
    }
  }, [user?.email, updateSettings]);

  const handleSettingChange = (key: keyof typeof settings, value: boolean | string) => {
    updateSettings({ [key]: value });
  };

  return (
    <div className="card">
      <div className="flex items-center gap-2 mb-4">
        <Settings size={20} className="text-gray-600" />
        <h3 className="text-lg font-semibold">Настройки уведомлений</h3>
      </div>

      <div className="space-y-4">
        <div className="flex items-center justify-between">
          <div className="flex items-center gap-3">
            <Mail size={16} className="text-gray-600" />
            <div>
              <p className="font-medium">Приветственное письмо</p>
              <p className="text-sm text-gray-500">
                Отправляется при регистрации нового пользователя
              </p>
            </div>
          </div>
          <label className="relative inline-flex items-center cursor-pointer">
            <input
              type="checkbox"
              checked={settings.welcomeEmail}
              onChange={(e) => handleSettingChange('welcomeEmail', e.target.checked)}
              className="sr-only peer"
            />
            <div className="w-11 h-6 bg-gray-200 peer-focus:outline-none peer-focus:ring-4 peer-focus:ring-blue-300 rounded-full peer peer-checked:after:translate-x-full peer-checked:after:border-white after:content-[''] after:absolute after:top-[2px] after:left-[2px] after:bg-white after:border-gray-300 after:border after:rounded-full after:h-5 after:w-5 after:transition-all peer-checked:bg-blue-600"></div>
          </label>
        </div>

        <div className="flex items-center justify-between">
          <div className="flex items-center gap-3">
            <Bell size={16} className="text-gray-600" />
            <div>
              <p className="font-medium">Ежедневный отчет</p>
              <p className="text-sm text-gray-500">
                Отправляется каждый день в полночь с статистикой задач
              </p>
            </div>
          </div>
          <label className="relative inline-flex items-center cursor-pointer">
            <input
              type="checkbox"
              checked={settings.dailyReport}
              onChange={(e) => handleSettingChange('dailyReport', e.target.checked)}
              className="sr-only peer"
            />
            <div className="w-11 h-6 bg-gray-200 peer-focus:outline-none peer-focus:ring-4 peer-focus:ring-blue-300 rounded-full peer peer-checked:after:translate-x-full peer-checked:after:border-white after:content-[''] after:absolute after:top-[2px] after:left-[2px] after:bg-white after:border-gray-300 after:border after:rounded-full after:h-5 after:w-5 after:transition-all peer-checked:bg-blue-600"></div>
          </label>
        </div>

        <div>
          <label htmlFor="notification-email" className="block text-sm font-medium text-gray-700 mb-1">
            Email для уведомлений
          </label>
          <input
            id="notification-email"
            type="email"
            value={settings.email}
            onChange={(e) => handleSettingChange('email', e.target.value)}
            className="input w-full"
            placeholder="Введите email для уведомлений"
          />
        </div>

        <div className="p-3 bg-blue-50 border border-blue-200 rounded">
          <p className="text-sm text-blue-800">
            <strong>Демо режим:</strong> В реальном приложении здесь будет интеграция с email сервисом для отправки уведомлений.
          </p>
        </div>
      </div>
    </div>
  );
};
