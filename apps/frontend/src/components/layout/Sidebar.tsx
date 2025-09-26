import { Home, Calendar, Users, Activity, BarChart3 } from 'lucide-react';
import { NavLink } from 'react-router-dom';
import { clsx } from 'clsx';

const navItems = [
  { icon: Home, label: 'Дашборд', path: '/' },
  { icon: Calendar, label: 'Мероприятия', path: '/events' },
  { icon: Users, label: 'Пользователи', path: '/users' },
  { icon: Activity, label: 'Активность', path: '/activity' },
  { icon: BarChart3, label: 'Аналитика', path: '/analytics' },
];

export const Sidebar = () => {
  return (
    <aside className="w-64 bg-gray-50 border-r border-gray-200 px-4 py-6">
      <nav className="space-y-2">
        {navItems.map((item) => {
          const Icon = item.icon;
          return (
            <NavLink
              key={item.path}
              to={item.path}
              className={({ isActive }) =>
                clsx(
                  'flex items-center px-3 py-2 rounded-lg text-sm font-medium transition-colors',
                  isActive
                    ? 'bg-primary-100 text-primary-700'
                    : 'text-gray-700 hover:bg-gray-200'
                )
              }
            >
              <Icon className="w-5 h-5 mr-3" />
              {item.label}
            </NavLink>
          );
        })}
      </nav>
    </aside>
  );
}; 