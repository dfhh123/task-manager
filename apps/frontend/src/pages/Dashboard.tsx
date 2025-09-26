import { Users, Calendar, Activity, TrendingUp } from 'lucide-react';
import { Card, Badge } from '../components/ui';
import { mockDashboardStats, mockEvents, mockActivities } from '../data/mockData';
import { format } from 'date-fns';
import { ru } from 'date-fns/locale';

const StatCard = ({ icon: Icon, title, value, description, trend }: {
  icon: any;
  title: string;
  value: number;
  description: string;
  trend?: string;
}) => (
  <div className="stat-card floating-card shimmer group">
    <div className="relative z-10">
      <div className="flex items-center justify-between mb-4">
        <div className="w-14 h-14 bg-gradient-to-br from-pink-500 to-purple-600 rounded-2xl flex items-center justify-center pulse-glow group-hover:scale-110 transition-transform duration-500">
          <Icon className="w-7 h-7 text-white" />
        </div>
        {trend && (
          <div className="text-right">
            <span className="inline-flex items-center px-2 py-1 rounded-full text-xs font-medium bg-green-100 text-green-800">
              ↗ {trend}
            </span>
          </div>
        )}
      </div>
      <div className="space-y-1">
        <h3 className="text-3xl font-bold bg-gradient-to-r from-gray-900 to-gray-700 bg-clip-text text-transparent">
          {value.toLocaleString()}
        </h3>
        <p className="text-sm font-medium text-gray-700">{title}</p>
        <p className="text-xs text-gray-500">{description}</p>
      </div>
    </div>
  </div>
);

export const Dashboard = () => {
  const recentEvents = mockEvents.slice(0, 3);
  const recentActivities = mockActivities.slice(0, 5);

  return (
    <div className="space-y-8 p-6">
      <div className="text-center space-y-4">
        <h1 className="text-5xl font-bold modern-title">Дашборд</h1>
        <p className="text-lg text-gray-600 max-w-2xl mx-auto">
          Управляйте вашей event-платформой с элегантностью и эффективностью
        </p>
        <div className="w-24 h-1 bg-gradient-to-r from-pink-500 to-blue-500 mx-auto rounded-full"></div>
      </div>

      {/* Статистика */}
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
        <StatCard
          icon={Users}
          title="Пользователи"
          value={mockDashboardStats.totalUsers}
          description={`${mockDashboardStats.activeUsers} активных`}
          trend="+12% за месяц"
        />
        <StatCard
          icon={Calendar}
          title="Мероприятия"
          value={mockDashboardStats.totalEvents}
          description={`${mockDashboardStats.upcomingEvents} предстоящих`}
          trend="+5% за неделю"
        />
        <StatCard
          icon={Activity}
          title="Активность"
          value={mockDashboardStats.totalActivities}
          description="За последние 7 дней"
          trend="+8% за день"
        />
        <StatCard
          icon={TrendingUp}
          title="Завершено"
          value={mockDashboardStats.completedEvents}
          description="Успешных событий"
        />
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-8">
        {/* Недавние мероприятия */}
        <div className="card-accent floating-card">
          <div className="flex items-center justify-between mb-6">
            <h2 className="text-2xl font-bold bg-gradient-to-r from-pink-600 to-purple-600 bg-clip-text text-transparent">
              Недавние мероприятия
            </h2>
            <Calendar className="w-6 h-6 text-pink-500" />
          </div>
          <div className="space-y-4">
            {recentEvents.map((event, index) => (
              <div 
                key={event.id} 
                className="group p-4 bg-white/60 backdrop-blur-sm rounded-xl border border-white/30 hover:bg-white/80 hover:scale-[1.02] transition-all duration-500"
                style={{ animationDelay: `${index * 100}ms` }}
              >
                <div className="flex items-center justify-between">
                  <div className="space-y-2">
                    <h3 className="font-semibold text-gray-900 group-hover:text-pink-600 transition-colors">
                      {event.title}
                    </h3>
                    <p className="text-sm text-gray-600 flex items-center">
                      <Calendar className="w-4 h-4 mr-2 text-pink-500" />
                      {format(new Date(event.startDate), 'dd MMMM yyyy, HH:mm', { locale: ru })}
                    </p>
                    <p className="text-xs text-gray-500">{event.location || 'Онлайн'}</p>
                  </div>
                  <div className="text-right space-y-2">
                    <Badge variant={
                      event.status === 'PUBLISHED' ? 'success' :
                      event.status === 'DRAFT' ? 'warning' :
                      event.status === 'COMPLETED' ? 'info' : 'default'
                    }>
                      {event.status === 'PUBLISHED' ? 'Опубликовано' :
                       event.status === 'DRAFT' ? 'Черновик' :
                       event.status === 'COMPLETED' ? 'Завершено' : event.status}
                    </Badge>
                    <p className="text-xs text-gray-500">
                      {event.currentParticipants}/{event.maxParticipants || '∞'} участников
                    </p>
                  </div>
                </div>
              </div>
            ))}
          </div>
        </div>

        {/* Последняя активность */}
        <div className="card-accent floating-card">
          <div className="flex items-center justify-between mb-6">
            <h2 className="text-2xl font-bold bg-gradient-to-r from-blue-600 to-purple-600 bg-clip-text text-transparent">
              Последняя активность
            </h2>
            <Activity className="w-6 h-6 text-blue-500" />
          </div>
          <div className="space-y-4">
            {recentActivities.map((activity, index) => (
              <div 
                key={activity.id} 
                className="group flex items-start space-x-4 p-3 rounded-xl hover:bg-white/40 transition-all duration-300"
                style={{ animationDelay: `${index * 50}ms` }}
              >
                <div className="flex-shrink-0">
                  <div className="w-10 h-10 bg-gradient-to-br from-blue-500 to-purple-600 rounded-full flex items-center justify-center group-hover:scale-110 transition-transform duration-300">
                    <Activity className="w-5 h-5 text-white" />
                  </div>
                </div>
                <div className="flex-1 min-w-0 space-y-1">
                  <p className="text-sm font-medium text-gray-900 group-hover:text-blue-600 transition-colors">
                    {activity.description}
                  </p>
                  <p className="text-xs text-gray-500 flex items-center">
                    <span className="font-medium">{activity.user?.firstName} {activity.user?.lastName}</span>
                    <span className="mx-2">•</span>
                    <span>{format(new Date(activity.timestamp), 'dd.MM.yyyy HH:mm', { locale: ru })}</span>
                  </p>
                </div>
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
}; 