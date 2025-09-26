import React from 'react';
import { LoginModal } from './components/LoginModal';
import { RegisterModal } from './components/RegisterModal';
import { useModalStore } from '../../stores/modalStore';

export const AuthModule: React.FC = () => {
  const { isLoginOpen, isRegisterOpen, openRegister, openLogin, closeModals } = useModalStore();

  return (
    <>
      <LoginModal
        isOpen={isLoginOpen}
        onClose={closeModals}
        onSwitchToRegister={openRegister}
      />
      <RegisterModal
        isOpen={isRegisterOpen}
        onClose={closeModals}
        onSwitchToLogin={openLogin}
      />
    </>
  );
};

// Экспорт для использования в других модулях
export { useAuthStore } from './stores/authStore';
export type { User } from './stores/authStore';
