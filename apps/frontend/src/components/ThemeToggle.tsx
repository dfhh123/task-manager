import { Moon, Sun } from 'lucide-react';
import { useTheme } from '../hooks/useTheme';

export const ThemeToggle = () => {
  const { theme, toggleTheme } = useTheme();

  return (
    <button
      onClick={toggleTheme}
      className="p-2 rounded-lg hover:bg-gray-100 dark:hover:bg-gray-800 transition-colors"
      aria-label="Переключить тему"
      title={theme === 'light' ? 'Темная тема' : 'Светлая тема'}
    >
      {theme === 'light' ? (
        <Moon size={20} className="icon-muted" />
      ) : (
        <Sun size={20} className="text-yellow-500 dark:text-yellow-400 transition-colors" />
      )}
    </button>
  );
};

