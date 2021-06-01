import React, { PureComponent } from 'react';
import { BrowserRouter, Switch, Redirect } from 'react-router-dom';
import { ConfigProvider, message } from 'antd';
import zhCN from 'antd/lib/locale-provider/zh_CN';
import { BASENAME } from '@/config/application';
import SessionStore from '@/session/SessionStore';
import RouteRender from '@/components/RouteRender';
import routes from '../routes';
import '@/static/css/antd.css';
import '@/static/css/public.css';
import '@/static/css/style.css';

message.config({ top: 120, duration: 2 });

class App extends PureComponent {
    render() {
        return (
            <ConfigProvider locale={zhCN}>
                <BrowserRouter basename={BASENAME} forceRefresh={false}>
                    <Switch>
                        {routes.map((route, i) => (
                            <RouteRender key={route.path} {...route} />
                        ))}
                        <RouteRender component={() => <Redirect push to={SessionStore.getHomePath()} />} />
                    </Switch>
                </BrowserRouter>
            </ConfigProvider>
        );
    }
}

export default App;
