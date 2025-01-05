import { Statistic } from 'antd';
import { Link } from 'react-router';

export default function LinkStatistic({ to, title, value, groupSeparator, suffix }) {
    const valueNode = (number) => <Link to={to}>{number}</Link>;

    return <Statistic title={title} groupSeparator={groupSeparator} suffix={suffix} valueRender={() => valueNode(value)} />;
}
