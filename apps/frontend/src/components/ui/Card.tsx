import { ReactNode } from 'react';
import { clsx } from 'clsx';

interface CardProps {
  children: ReactNode;
  className?: string;
  padding?: 'sm' | 'md' | 'lg';
  variant?: 'default' | 'accent' | 'glass' | 'floating';
  glow?: boolean;
}

export const Card = ({ 
  children, 
  className, 
  padding = 'md',
  variant = 'default',
  glow = false 
}: CardProps) => {
  const paddingClasses = {
    sm: 'p-4',
    md: 'p-6',
    lg: 'p-8',
  };

  const variantClasses = {
    default: 'card',
    accent: 'card-accent',
    glass: 'card-glass',
    floating: 'card floating-card'
  };

  return (
    <div className={clsx(
      variantClasses[variant],
      paddingClasses[padding],
      glow && 'glow-pink',
      className
    )}>
      {children}
    </div>
  );
}; 