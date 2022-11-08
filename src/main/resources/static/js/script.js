function getRegression(yPoints) {
    const stepList = this.getStepList(15, 7);

    const dataPoints = this.getPairs(stepList, yPoints);

    const clean_data = dataPoints
        .filter(({x, y}) => {
            return (
                typeof x === typeof y &&
                !isNaN(x) &&
                !isNaN(y) &&
                Math.abs(x) !== Infinity &&
                Math.abs(y) !== Infinity
            );
        })
        .map(({x, y}) => {
            return [x, y];
        });

    const my_regression = regression.linear(
        clean_data
    );

    return my_regression.points.map(([x, y]) => {
        return y;
    });
}

function getStepList(step, size) {
    const stepList = [];
    for (let i = 1; i <= size; i++) {
        stepList.push(step * i);
    }
    return stepList;
}

function getPairs(xPoints, yPoints) {
    const dataPoints = [];
    for (let i = 0; i < xPoints.length; i++) {
        dataPoints.push({x: xPoints[i], y: yPoints[i]});
    }
    return dataPoints;
}
