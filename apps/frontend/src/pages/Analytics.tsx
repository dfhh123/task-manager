import { BarChart3, TrendingUp, Users, Calendar, PieChart } from 'lucide-react';
import { Card } from '../components/ui';
import { mockDashboardStats } from '../data/mockData';

export const Analytics = () => {
  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-3xl font-bold text-gray-900">Аналитика</h1>
        <p className="text-gray-600">Подробная аналитика и отчеты</p>
      </div>

      {/* Ключевые метрики */}
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
        <Card>
          <div className="flex items-center">
            <div className="w-12 h-12 bg-blue-100 rounded-lg flex items-center justify-center">
              <Users className="w-6 h-6 text-blue-600" />
            </div>
            <div className="ml-4">
              <h3 className="text-2xl font-bold text-gray-900">{mockDashboardStats.totalUsers}</h3>
              <p className="text-sm text-gray-600">Всего пользователей</p>
              <p className="text-xs text-green-600">↗ +12% за месяц</p>
            </div>
          </div>
        </Card>

        <Card>
          <div className="flex items-center">
            <div className="w-12 h-12 bg-green-100 rounded-lg flex items-center justify-center">
              <Calendar className="w-6 h-6 text-green-600" />
            </div>
            <div className="ml-4">
              <h3 className="text-2xl font-bold text-gray-900">{mockDashboardStats.totalEvents}</h3>
              <p className="text-sm text-gray-600">Всего событий</p>
              <p className="text-xs text-green-600">↗ +8% за неделю</p>
            </div>
          </div>
        </Card>

        <Card>
          <div className="flex items-center">
            <div className="w-12 h-12 bg-yellow-100 rounded-lg flex items-center justify-center">
              <TrendingUp className="w-6 h-6 text-yellow-600" />
            </div>
            <div className="ml-4">
              <h3 className="text-2xl font-bold text-gray-900">85%</h3>
              <p className="text-sm text-gray-600">Участие в событиях</p>
              <p className="text-xs text-green-600">↗ +5% за месяц</p>
            </div>
          </div>
        </Card>

        <Card>
          <div className="flex items-center">
            <div className="w-12 h-12 bg-purple-100 rounded-lg flex items-center justify-center">
              <BarChart3 className="w-6 h-6 text-purple-600" />
            </div>
            <div className="ml-4">
              <h3 className="text-2xl font-bold text-gray-900">4.8</h3>
              <p className="text-sm text-gray-600">Средняя оценка</p>
              <p className="text-xs text-green-600">↗ +0.2 за месяц</p>
            </div>
          </div>
        </Card>
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        {/* График активности пользователей */}
        <Card>
          <div className="flex items-center justify-between mb-4">
            <h2 className="text-xl font-semibold text-gray-900">Активность пользователей</h2>
            <BarChart3 className="w-5 h-5 text-gray-400" />
          </div>
          <div className="h-64 bg-gray-50 rounded-lg flex items-center justify-center">
            <div className="text-center text-gray-500">
              <BarChart3 className="w-12 h-12 mx-auto mb-2" />
              <p>График активности пользователей</p>
              <p className="text-sm">(В демо-версии недоступно)</p>
            </div>
          </div>
        </Card>

        {/* Распределение событий по типам */}
        <Card>
          <div className="flex items-center justify-between mb-4">
            <h2 className="text-xl font-semibold text-gray-900">События по типам</h2>
            <PieChart className="w-5 h-5 text-gray-400" />
          </div>
          <div className="space-y-3">
            <div className="flex items-center justify-between">
              <div className="flex items-center">
                <div className="w-3 h-3 bg-blue-500 rounded-full mr-2"></div>
                <span className="text-sm text-gray-600">Конференции</span>
              </div>
              <span className="text-sm font-medium">25%</span>
            </div>
            <div className="flex items-center justify-between">
              <div className="flex items-center">
                <div className="w-3 h-3 bg-green-500 rounded-full mr-2"></div>
                <span className="text-sm text-gray-600">Воркшопы</span>
              </div>
              <span className="text-sm font-medium">35%</span>
            </div>
            <div className="flex items-center justify-between">
              <div className="flex items-center">
                <div className="w-3 h-3 bg-yellow-500 rounded-full mr-2"></div>
                <span className="text-sm text-gray-600">Митапы</span>
              </div>
              <span className="text-sm font-medium">30%</span>
            </div>
            <div className="flex items-center justify-between">
              <div className="flex items-center">
                <div className="w-3 h-3 bg-purple-500 rounded-full mr-2"></div>
                <span className="text-sm text-gray-600">Вебинары</span>
              </div>
              <span className="text-sm font-medium">10%</span>
            </div>
          </div>
        </Card>
      </div>

      {/* Временные тренды */}
      <Card>
        <h2 className="text-xl font-semibold text-gray-900 mb-4">Тренды за последние 30 дней</h2>
        <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
          <div className="text-center">
            <div className="text-3xl font-bold text-blue-600 mb-2">142</div>
            <div className="text-sm text-gray-600 mb-1">Новые пользователи</div>
            <div className="text-xs text-green-600">+18% к прошлому месяцу</div>
          </div>
          <div className="text-center">
            <div className="text-3xl font-bold text-green-600 mb-2">28</div>
            <div className="text-sm text-gray-600 mb-1">Новые события</div>
            <div className="text-xs text-green-600">+12% к прошлому месяцу</div>
          </div>
          <div className="text-center">
            <div className="text-3xl font-bold text-purple-600 mb-2">1,287</div>
            <div className="text-sm text-gray-600 mb-1">Участия в событиях</div>
            <div className="text-xs text-green-600">+24% к прошлому месяцу</div>
          </div>
        </div>
      </Card>
    </div>
  );
}; 