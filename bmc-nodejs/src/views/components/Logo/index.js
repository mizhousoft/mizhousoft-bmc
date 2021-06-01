import React, { PureComponent } from 'react';
import { APPLICATION_NAME } from '@/config/application';

class Logo extends PureComponent {
    render() {
        return <div className='logo'>{APPLICATION_NAME}</div>;
    }
}

export default Logo;
