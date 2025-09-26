import { create } from 'zustand';

export interface NotificationSettings {
  welcomeEmail: boolean;
  dailyReport: boolean;
  email: string;
}

interface NotificationState {
  settings: NotificationSettings;
  isDailyReportSent: boolean;
  lastReportDate: string | null;
  
  // Actions
  updateSettings: (settings: Partial<NotificationSettings>) => void;
  sendWelcomeEmail: () => Promise<void>;
  sendDailyReport: () => Promise<void>;
  checkDailyReport: () => void;
}

export const useNotificationStore = create<NotificationState>((set, get) => ({
  settings: {
    welcomeEmail: true,
    dailyReport: true,
    email: '',
  },
  isDailyReportSent: false,
  lastReportDate: null,

  updateSettings: (newSettings) => {
    set(state => ({
      settings: { ...state.settings, ...newSettings }
    }));
  },

  sendWelcomeEmail: async () => {
    const { settings } = get();
    
    if (!settings.welcomeEmail || !settings.email) return;
    
    try {
      // Симуляция отправки email
      await new Promise(resolve => setTimeout(resolve, 1000));
      
      console.log('Welcome email sent to:', settings.email);
      
      // В реальном приложении здесь был бы API вызов
      // await api.post('/notifications/welcome', { email: settings.email });
    } catch (error) {
      console.error('Failed to send welcome email:', error);
    }
  },

  sendDailyReport: async () => {
    const { settings, isDailyReportSent, lastReportDate } = get();
    
    if (!settings.dailyReport || !settings.email || isDailyReportSent) return;
    
    const today = new Date().toDateString();
    if (lastReportDate === today) return;
    
    try {
      // Симуляция отправки daily report
      await new Promise(resolve => setTimeout(resolve, 1000));
      
      console.log('Daily report sent to:', settings.email);
      
      set({
        isDailyReportSent: true,
        lastReportDate: today,
      });
      
      // В реальном приложении здесь был бы API вызов
      // await api.post('/notifications/daily-report', { email: settings.email });
    } catch (error) {
      console.error('Failed to send daily report:', error);
    }
  },

  checkDailyReport: () => {
    const { settings, lastReportDate } = get();
    
    if (!settings.dailyReport || !settings.email) return;
    
    const today = new Date().toDateString();
    const now = new Date();
    const midnight = new Date(now.getFullYear(), now.getMonth(), now.getDate() + 1, 0, 0, 0);
    const timeUntilMidnight = midnight.getTime() - now.getTime();
    
    // Если сегодня еще не отправляли отчет и наступила полночь
    if (lastReportDate !== today && now.getHours() === 0 && now.getMinutes() === 0) {
      get().sendDailyReport();
    }
    
    // Устанавливаем таймер на следующую полночь
    setTimeout(() => {
      get().sendDailyReport();
      // Рекурсивно устанавливаем таймер на следующий день
      setInterval(() => {
        get().sendDailyReport();
      }, 24 * 60 * 60 * 1000); // 24 часа
    }, timeUntilMidnight);
  },
}));
