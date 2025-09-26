import React, { lazy, Suspense } from 'react';
import { UserModuleProvider } from './providers/UserModuleProvider';
import { UserModuleRoutes } from './routes/UserModuleRoutes';

// Lazy loading для компонентов модуля
const UserDashboard = lazy(() => import('./components/UserDashboard'));
const UserProfile = lazy(() => import('./components/UserProfile'));
const UserSettings = lazy(() => import('./components/UserSettings'));

interface UserModuleProps {
  basePath?: string;
}

export const UserModule: React.FC<UserModuleProps> = ({ basePath = '/users' }) => {
  return (
    <UserModuleProvider>
      <Suspense fallback={<div>Loading user module...</div>}>
        <UserModuleRoutes basePath={basePath}>
          {/* Маршруты модуля */}
        </UserModuleRoutes>
      </Suspense>
    </UserModuleProvider>
  );
};

// Экспорт для использования в других модулях
export * from './hooks';
export * from './types';
export * from './services';
