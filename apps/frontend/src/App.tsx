import { Routes, Route } from 'react-router-dom';
import { Layout } from './components/layout/Layout';
import { AuthModule } from './modules/auth/AuthModule';
import { TaskModule } from './modules/tasks/TaskModule';
import { NotificationModule } from './modules/notifications/NotificationModule';
import { DemoLoader } from './components/demo/DemoLoader';
import { useAuthStore } from './modules/auth/stores/authStore';

function App() {
  const { isAuthenticated } = useAuthStore();

  return (
    <div className="min-h-screen bg-gray-50">
      <Layout>
        <Routes>
          <Route path="/" element={
            isAuthenticated ? (
              <TaskModule />
            ) : (
              <div className="flex items-center justify-center h-96">
                <div className="text-center">
                  <h2 className="text-2xl font-semibold text-gray-700 mb-4">
                    Добро пожаловать в Планировщик задач
                  </h2>
                  <p className="text-gray-500">
                    Войдите или зарегистрируйтесь, чтобы начать работу с задачами
                  </p>
                </div>
              </div>
            )
          } />
          <Route path="/notifications" element={
            isAuthenticated ? (
              <NotificationModule />
            ) : (
              <div className="flex items-center justify-center h-96">
                <div className="text-center">
                  <h2 className="text-2xl font-semibold text-gray-700 mb-4">
                    Необходима авторизация
                  </h2>
                  <p className="text-gray-500">
                    Войдите в систему для доступа к настройкам
                  </p>
                </div>
              </div>
            )
          } />
        </Routes>
      </Layout>
      
      {/* Модули */}
      <AuthModule />
      <NotificationModule />
      <DemoLoader />
    </div>
  );
}

export default App;