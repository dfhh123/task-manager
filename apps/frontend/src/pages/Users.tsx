import { User, Mail, Shield, Clock, Search, Plus, MoreHorizontal } from 'lucide-react';
import { Card, Badge, Button } from '../components/ui';
import { mockUsers } from '../data/mockData';
import { format } from 'date-fns';

export const Users = () => {
  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <div>
          <h1 className="text-3xl font-bold text-gray-900">Пользователи</h1>
          <p className="text-gray-600">Управление пользователями системы</p>
        </div>
        <Button>
          <Plus className="w-4 h-4 mr-2" />
          Добавить пользователя
        </Button>
      </div>

      {/* Поиск */}
      <Card>
        <div className="flex items-center space-x-4">
          <div className="flex-1">
            <div className="relative">
              <Search className="w-4 h-4 absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400" />
              <input
                type="text"
                placeholder="Поиск пользователей..."
                className="w-full pl-10 pr-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-primary-500 focus:border-transparent"
              />
            </div>
          </div>
          <select className="px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-primary-500 focus:border-transparent">
            <option value="">Все роли</option>
            <option value="ADMIN">Администратор</option>
            <option value="MODERATOR">Модератор</option>
            <option value="USER">Пользователь</option>
          </select>
          <select className="px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-primary-500 focus:border-transparent">
            <option value="">Все статусы</option>
            <option value="active">Активные</option>
            <option value="inactive">Неактивные</option>
          </select>
        </div>
      </Card>

      {/* Таблица пользователей */}
      <Card>
        <div className="overflow-x-auto">
          <table className="w-full">
            <thead>
              <tr className="border-b border-gray-200">
                <th className="text-left py-3 px-4 font-medium text-gray-900">Пользователь</th>
                <th className="text-left py-3 px-4 font-medium text-gray-900">Роль</th>
                <th className="text-left py-3 px-4 font-medium text-gray-900">Статус</th>
                <th className="text-left py-3 px-4 font-medium text-gray-900">Последний вход</th>
                <th className="text-left py-3 px-4 font-medium text-gray-900">Дата регистрации</th>
                <th className="text-right py-3 px-4 font-medium text-gray-900">Действия</th>
              </tr>
            </thead>
            <tbody>
              {mockUsers.map((user) => (
                <tr key={user.id} className="border-b border-gray-100 hover:bg-gray-50">
                  <td className="py-4 px-4">
                    <div className="flex items-center space-x-3">
                      <div className="w-10 h-10 bg-primary-100 rounded-full flex items-center justify-center">
                        <User className="w-5 h-5 text-primary-600" />
                      </div>
                      <div>
                        <div className="font-medium text-gray-900">
                          {user.firstName} {user.lastName}
                        </div>
                        <div className="text-sm text-gray-500 flex items-center">
                          <Mail className="w-3 h-3 mr-1" />
                          {user.email}
                        </div>
                      </div>
                    </div>
                  </td>
                  <td className="py-4 px-4">
                    <Badge variant={
                      user.role === 'ADMIN' ? 'error' :
                      user.role === 'MODERATOR' ? 'warning' : 'default'
                    }>
                      <Shield className="w-3 h-3 mr-1" />
                      {user.role === 'ADMIN' ? 'Администратор' :
                       user.role === 'MODERATOR' ? 'Модератор' : 'Пользователь'}
                    </Badge>
                  </td>
                  <td className="py-4 px-4">
                    <Badge variant={user.isActive ? 'success' : 'default'}>
                      {user.isActive ? 'Активен' : 'Неактивен'}
                    </Badge>
                  </td>
                  <td className="py-4 px-4">
                    <div className="text-sm text-gray-900 flex items-center">
                      <Clock className="w-3 h-3 mr-1" />
                      {user.lastLogin 
                        ? format(new Date(user.lastLogin), 'dd.MM.yyyy HH:mm')
                        : 'Никогда'
                      }
                    </div>
                  </td>
                  <td className="py-4 px-4">
                    <div className="text-sm text-gray-500">
                      {format(new Date(user.createdAt), 'dd.MM.yyyy')}
                    </div>
                  </td>
                  <td className="py-4 px-4 text-right">
                    <div className="flex items-center justify-end space-x-2">
                      <Button variant="outline" size="sm">
                        Изменить
                      </Button>
                      <Button variant="outline" size="sm">
                        <MoreHorizontal className="w-4 h-4" />
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </Card>

      {/* Статистика */}
      <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
        <Card>
          <div className="text-center">
            <div className="text-2xl font-bold text-gray-900">{mockUsers.length}</div>
            <div className="text-sm text-gray-600">Всего пользователей</div>
          </div>
        </Card>
        <Card>
          <div className="text-center">
            <div className="text-2xl font-bold text-green-600">
              {mockUsers.filter(u => u.isActive).length}
            </div>
            <div className="text-sm text-gray-600">Активных пользователей</div>
          </div>
        </Card>
        <Card>
          <div className="text-center">
            <div className="text-2xl font-bold text-blue-600">
              {mockUsers.filter(u => u.role === 'ADMIN').length}
            </div>
            <div className="text-sm text-gray-600">Администраторов</div>
          </div>
        </Card>
      </div>
    </div>
  );
}; 