import React from 'react';
import { Link } from 'react-router-dom';

export default function SafeLink({ to, style, children }) {
    return (
        <Link to={to} rel='opener' target='_blank' style={style}>
            {children}
        </Link>
    );
}
