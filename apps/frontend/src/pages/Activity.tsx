import { Activity as ActivityIcon, User, Calendar, LogIn, LogOut, Plus, Search, Filter } from 'lucide-react';
import { Card, Badge } from '../components/ui';
import { mockActivities } from '../data/mockData';
import { format } from 'date-fns';

const getActivityIcon = (type: string) => {
  switch (type) {
    case 'LOGIN':
      return LogIn;
    case 'LOGOUT':
      return LogOut;
    case 'EVENT_CREATED':
    case 'EVENT_UPDATED':
    case 'EVENT_DELETED':
      return Calendar;
    case 'USER_REGISTERED':
      return Plus;
    default:
      return ActivityIcon;
  }
};

const getActivityColor = (type: string) => {
  switch (type) {
    case 'LOGIN':
      return 'success';
    case 'LOGOUT':
      return 'default';
    case 'EVENT_CREATED':
      return 'info';
    case 'EVENT_UPDATED':
      return 'warning';
    case 'EVENT_DELETED':
      return 'error';
    case 'USER_REGISTERED':
      return 'success';
    default:
      return 'default';
  }
};

export const Activity = () => {
  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-3xl font-bold text-gray-900">Активность</h1>
        <p className="text-gray-600">Мониторинг активности пользователей</p>
      </div>

      {/* Фильтры */}
      <Card>
        <div className="flex items-center space-x-4">
          <div className="flex-1">
            <div className="relative">
              <Search className="w-4 h-4 absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400" />
              <input
                type="text"
                placeholder="Поиск активности..."
                className="w-full pl-10 pr-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-primary-500 focus:border-transparent"
              />
            </div>
          </div>
          <select className="px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-primary-500 focus:border-transparent">
            <option value="">Все типы</option>
            <option value="LOGIN">Вход в систему</option>
            <option value="LOGOUT">Выход из системы</option>
            <option value="EVENT_CREATED">Создание события</option>
            <option value="EVENT_UPDATED">Обновление события</option>
            <option value="USER_REGISTERED">Регистрация пользователя</option>
          </select>
          <input
            type="date"
            className="px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-primary-500 focus:border-transparent"
          />
        </div>
      </Card>

      {/* Лента активности */}
      <Card>
        <h2 className="text-xl font-semibold text-gray-900 mb-6">Последняя активность</h2>
        <div className="space-y-4">
          {mockActivities.map((activity, index) => {
            const Icon = getActivityIcon(activity.type);
            const color = getActivityColor(activity.type);
            
            return (
              <div key={activity.id} className="relative">
                {index !== mockActivities.length - 1 && (
                  <div className="absolute left-6 top-12 w-px h-12 bg-gray-200"></div>
                )}
                <div className="flex items-start space-x-4">
                  <div className={`flex-shrink-0 w-12 h-12 rounded-full flex items-center justify-center ${
                    color === 'success' ? 'bg-green-100' :
                    color === 'warning' ? 'bg-yellow-100' :
                    color === 'error' ? 'bg-red-100' :
                    color === 'info' ? 'bg-blue-100' : 'bg-gray-100'
                  }`}>
                    <Icon className={`w-5 h-5 ${
                      color === 'success' ? 'text-green-600' :
                      color === 'warning' ? 'text-yellow-600' :
                      color === 'error' ? 'text-red-600' :
                      color === 'info' ? 'text-blue-600' : 'text-gray-600'
                    }`} />
                  </div>
                  
                  <div className="flex-1 min-w-0">
                    <div className="flex items-center justify-between">
                      <div className="flex items-center space-x-2">
                        <span className="font-medium text-gray-900">
                          {activity.user?.firstName} {activity.user?.lastName}
                        </span>
                        <Badge variant={color as any}>
                          {activity.type === 'LOGIN' ? 'Вход' :
                           activity.type === 'LOGOUT' ? 'Выход' :
                           activity.type === 'EVENT_CREATED' ? 'Создание' :
                           activity.type === 'EVENT_UPDATED' ? 'Обновление' :
                           activity.type === 'EVENT_DELETED' ? 'Удаление' :
                           activity.type === 'USER_REGISTERED' ? 'Регистрация' : activity.type}
                        </Badge>
                      </div>
                      <div className="text-sm text-gray-500">
                        {format(new Date(activity.timestamp), 'dd.MM.yyyy HH:mm')}
                      </div>
                    </div>
                    
                    <p className="text-gray-600 mt-1">{activity.description}</p>
                    
                    <div className="flex items-center space-x-4 mt-2 text-xs text-gray-500">
                      <div className="flex items-center">
                        <User className="w-3 h-3 mr-1" />
                        {activity.user?.email}
                      </div>
                      {activity.ipAddress && (
                        <div>
                          IP: {activity.ipAddress}
                        </div>
                      )}
                      {activity.metadata && (
                        <div>
                          Метаданные: {Object.keys(activity.metadata).length} полей
                        </div>
                      )}
                    </div>
                  </div>
                </div>
              </div>
            );
          })}
        </div>
      </Card>

      {/* Статистика по типам активности */}
      <div className="grid grid-cols-2 md:grid-cols-4 gap-4">
        <Card padding="sm">
          <div className="text-center">
            <div className="text-lg font-bold text-green-600">
              {mockActivities.filter(a => a.type === 'LOGIN').length}
            </div>
            <div className="text-xs text-gray-600">Входов</div>
          </div>
        </Card>
        <Card padding="sm">
          <div className="text-center">
            <div className="text-lg font-bold text-blue-600">
              {mockActivities.filter(a => a.type.includes('EVENT')).length}
            </div>
            <div className="text-xs text-gray-600">Действий с событиями</div>
          </div>
        </Card>
        <Card padding="sm">
          <div className="text-center">
            <div className="text-lg font-bold text-purple-600">
              {mockActivities.filter(a => a.type === 'USER_REGISTERED').length}
            </div>
            <div className="text-xs text-gray-600">Регистраций</div>
          </div>
        </Card>
        <Card padding="sm">
          <div className="text-center">
            <div className="text-lg font-bold text-gray-600">
              {mockActivities.length}
            </div>
            <div className="text-xs text-gray-600">Всего событий</div>
          </div>
        </Card>
      </div>
    </div>
  );
}; 