// 标准音A4频率（Hz）
const standardFrequency = 440
/**
 * 生成频率表，入口函数
 * @param {*} sdf 标准音频率
 */
function genFreq(sdf){
    // 开始音A-1的频率(A0的低八度)
    const A00 = sdf / (1<<5)
    // 相邻半音比率
    const rate = Math.pow(2,1/12)
    // 音名数组
    const notes = ['A', 'AB', 'B', 'C', 'CD', 'D', 'DE', 'E', 'F', 'FG', 'G', 'GA','A']
    const frequencyMap = new Map()

    let octaveMap = new Map()
    let curFreq = 0
    for (let i=0; i<128;i++){
        const note = notes[i % 12]
        const no = Math.floor((i-3) / 12)
        let noteKey = note + no
        
        // 计算第i个位置的音高
        if (i % 12 == 0){
            curFreq = calibrate(sdf)
        } else {
            curFreq = Math.round(curFreq * rate * 10000) / 10000
        }
        
        // 替换半音音名
        if (note.length == 2){
            noteKey = `${note.slice()[0]}${no}#/${note.slice()}${no}b`
        } 
        octaveMap.set(noteKey,`${curFreq}`)
        if (Math.floor((i-2) % 12)==0){
            frequencyMap.set(notes[(i % 12)+1]+no, octaveMap)
            octaveMap = new Map()
        }
    }

    return frequencyMap
}
const calibrate = (sdf) => {
    if (i/12 < 5){
        return sdf / (1 << (5- i / 12))
    }

    return curFreq = sdf * 1<<(i / 12 - 5)
}
// genFreq(standardFrequency)
console.log(genFreq(standardFrequency));

