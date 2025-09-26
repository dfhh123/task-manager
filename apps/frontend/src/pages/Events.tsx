import { Calendar, MapPin, Users, Plus, Search, Filter } from 'lucide-react';
import { Card, Badge, Button } from '../components/ui';
import { mockEvents } from '../data/mockData';
import { format } from 'date-fns';

export const Events = () => {
  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <div>
          <h1 className="text-3xl font-bold text-gray-900">Мероприятия</h1>
          <p className="text-gray-600">Управление событиями платформы</p>
        </div>
        <Button>
          <Plus className="w-4 h-4 mr-2" />
          Создать мероприятие
        </Button>
      </div>

      {/* Фильтры */}
      <Card>
        <div className="flex items-center space-x-4">
          <div className="flex-1">
            <div className="relative">
              <Search className="w-4 h-4 absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400" />
              <input
                type="text"
                placeholder="Поиск мероприятий..."
                className="w-full pl-10 pr-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-primary-500 focus:border-transparent"
              />
            </div>
          </div>
          <Button variant="outline">
            <Filter className="w-4 h-4 mr-2" />
            Фильтры
          </Button>
        </div>
      </Card>

      {/* Список мероприятий */}
      <div className="grid grid-cols-1 lg:grid-cols-2 xl:grid-cols-3 gap-6">
        {mockEvents.map((event) => (
          <Card key={event.id} className="hover:shadow-md transition-shadow">
            <div className="space-y-4">
              <div className="flex items-start justify-between">
                <div className="flex-1">
                  <h3 className="text-lg font-semibold text-gray-900">{event.title}</h3>
                  <Badge variant={
                    event.type === 'CONFERENCE' ? 'info' :
                    event.type === 'WORKSHOP' ? 'success' :
                    event.type === 'MEETUP' ? 'warning' :
                    event.type === 'WEBINAR' ? 'default' : 'default'
                  }>
                    {event.type === 'CONFERENCE' ? 'Конференция' :
                     event.type === 'WORKSHOP' ? 'Воркшоп' :
                     event.type === 'MEETUP' ? 'Митап' :
                     event.type === 'WEBINAR' ? 'Вебинар' :
                     event.type === 'COMPETITION' ? 'Соревнование' : event.type}
                  </Badge>
                </div>
                <Badge variant={
                  event.status === 'PUBLISHED' ? 'success' :
                  event.status === 'DRAFT' ? 'warning' :
                  event.status === 'COMPLETED' ? 'info' : 'error'
                }>
                  {event.status === 'PUBLISHED' ? 'Опубликовано' :
                   event.status === 'DRAFT' ? 'Черновик' :
                   event.status === 'COMPLETED' ? 'Завершено' :
                   event.status === 'CANCELLED' ? 'Отменено' : event.status}
                </Badge>
              </div>

              <p className="text-gray-600 text-sm line-clamp-3">{event.description}</p>

              <div className="space-y-2">
                <div className="flex items-center text-sm text-gray-500">
                  <Calendar className="w-4 h-4 mr-2" />
                  {format(new Date(event.startDate), 'dd.MM.yyyy HH:mm')}
                </div>
                {event.location && (
                  <div className="flex items-center text-sm text-gray-500">
                    <MapPin className="w-4 h-4 mr-2" />
                    {event.location}
                  </div>
                )}
                <div className="flex items-center text-sm text-gray-500">
                  <Users className="w-4 h-4 mr-2" />
                  {event.currentParticipants} из {event.maxParticipants || '∞'} участников
                </div>
              </div>

              <div className="flex items-center justify-between pt-4 border-t border-gray-200">
                <div className="text-sm text-gray-500">
                  Организатор: {event.organizer?.firstName} {event.organizer?.lastName}
                </div>
                <div className="flex space-x-2">
                  <Button variant="outline" size="sm">
                    Изменить
                  </Button>
                  <Button variant="secondary" size="sm">
                    Подробнее
                  </Button>
                </div>
              </div>
            </div>
          </Card>
        ))}
      </div>
    </div>
  );
}; 